package com.example.manitas.screens.quiz

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen() {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var currentQuestion by remember { mutableStateOf(1) }
    val totalQuestions = 10

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { /* Navegar atrás */ }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Quiz",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            // Subtítulo
            Text(
                text = "Frutas",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Área del video (cuadro gris por ahora)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .background(
                        color = Color(0xFFBDBDBD),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Área del Video",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }

            // Contador de preguntas
            Text(
                text = "$currentQuestion / $totalQuestions",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // Opciones de respuesta (2x2 grid)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AnswerButton(
                        text = "Manzana",
                        isSelected = selectedAnswer == "Manzana",
                        onClick = { selectedAnswer = "Manzana" },
                        modifier = Modifier.weight(1f)
                    )
                    AnswerButton(
                        text = "Plátano",
                        isSelected = selectedAnswer == "Plátano",
                        onClick = { selectedAnswer = "Plátano" },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AnswerButton(
                        text = "Naranja",
                        isSelected = selectedAnswer == "Naranja",
                        onClick = { selectedAnswer = "Naranja" },
                        modifier = Modifier.weight(1f)
                    )
                    AnswerButton(
                        text = "Fresa",
                        isSelected = selectedAnswer == "Fresa",
                        onClick = { selectedAnswer = "Fresa" },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Botón de siguiente/reproducir
            FloatingActionButton(
                onClick = {
                    // Lógica para siguiente pregunta
                    if (currentQuestion < totalQuestions) {
                        currentQuestion++
                        selectedAnswer = null
                    }
                },
                containerColor = Color(0xFFB3E5FC),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Siguiente",
                    tint = Color.White
                )
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
        Text(
            text = text,
            fontSize = 16.sp
        )
    }
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    MaterialTheme {
        QuizScreen()
    }
}
