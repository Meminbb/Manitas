package com.example.manitas.screens.quiz

import android.net.Uri
import android.widget.FrameLayout
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.WindowInsets
import com.example.manitas.model.MediaType
import com.example.manitas.model.getQuizQuestionsbyCat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    categoryId: Int,
    categoryName: String,
    onBack: () -> Unit,
    onQuizFinished: (score: Int) -> Unit
) {
    val questions = remember(categoryId) { getQuizQuestionsbyCat(categoryId) }

    if (questions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No hay preguntas disponibles")
        }
        return
    }

    // States
    var currentIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var correctAnswers by remember { mutableStateOf(0) }
    var showCorrection by remember { mutableStateOf(false) }
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }

    val currentQuestion = questions[currentIndex]
    val totalQuestions = questions.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                windowInsets = WindowInsets(0)
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Título

            Text(
                text = "Quiz",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                color = Color.Black
            )

            // Categoría
            Text(
                text = categoryName,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp),
            )

            // Pregunta
            Text(
                text = currentQuestion.questionText,
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                color = Color.Black
            )

            // MEDIA (VIDEO o IMAGEN)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                when (currentQuestion.mediaType) {
                    MediaType.VIDEO -> {
                        SimpleVideoPlayer(mediaId = currentQuestion.mediaId)
                    }
                    MediaType.IMAGE -> {
                        Image(
                            painter = painterResource(id = currentQuestion.mediaId),
                            contentDescription = currentQuestion.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            // Contador de preguntas
            Text(
                text = "${currentIndex + 1} / $totalQuestions",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Opciones de respuesta
            if (!showCorrection) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val answers = currentQuestion.answerOptions

                    answers.chunked(2).forEachIndexed { rowIndex, row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            row.forEachIndexed { colIndex, optionText ->
                                val optionIndex = rowIndex * 2 + colIndex

                                AnswerButton(
                                    text = optionText,
                                    isSelected = selectedAnswerIndex == optionIndex,
                                    onClick = { selectedAnswerIndex = optionIndex },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showCorrection) {

                if (isAnswerCorrect == true) {
                    Text(
                        text = "¡Correcto!",
                        fontSize = 20.sp,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .background(Color(0xFFC8E6C9), RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Muy bien, sigue así!",
                            fontSize = 18.sp,
                            color = Color(0xFF1B5E20)
                        )
                    }
                }

                if (isAnswerCorrect == false) {
                    Text(
                        text = "Respuesta Incorrecta",
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .background(Color(0xFFB2FF59), RoundedCornerShape(12.dp))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Respuesta correcta: ${currentQuestion.answerOptions[currentQuestion.correctAnswer]}",
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                }

                Button(
                    onClick = {
                        if (currentIndex < totalQuestions - 1) {
                            currentIndex++
                            selectedAnswerIndex = null
                            showCorrection = false
                            isAnswerCorrect = null
                        } else {
                            val score = (correctAnswers * 100) / totalQuestions
                            onQuizFinished(score)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBED2E0),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Siguiente", fontSize = 18.sp)
                }

            } else {
                // Botón de enviar
                Button(
                    onClick = {
                        val selected = selectedAnswerIndex ?: return@Button
                        isAnswerCorrect = selected == currentQuestion.correctAnswer

                        if (isAnswerCorrect == true) correctAnswers++
                        showCorrection = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    enabled = selectedAnswerIndex != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBED2E0),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Enviar", fontSize = 18.sp)
                }

            }
        }
    }
}

@Composable
fun AnswerButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFB3E5FC) else Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(28.dp),
        border = androidx.compose.foundation.BorderStroke(
            width = 2.dp,
            color = if (isSelected) Color(0xFF4FC3F7) else Color(0xFFE0E0E0)
        )
    ) {
        Text(text, fontSize = 16.sp)
    }
}

@Composable
fun SimpleVideoPlayer(mediaId: Int) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            VideoView(ctx).apply {
                val uri = Uri.parse("android.resource://${ctx.packageName}/$mediaId")
                setVideoURI(uri)
                setOnCompletionListener { start() }
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT
                )
                start()
            }
        },
        update = { view ->
            val uri = Uri.parse("android.resource://${context.packageName}/$mediaId")
            view.setVideoURI(uri)
            view.setOnCompletionListener { view.start() }
            view.start()
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
        QuizScreen(
            categoryId = 1,
            categoryName = "Frutas",
            onBack = {},
            onQuizFinished = {}
        )
    }
}
