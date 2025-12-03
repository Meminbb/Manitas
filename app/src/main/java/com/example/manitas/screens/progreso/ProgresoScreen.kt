package com.example.manitas.screens.progreso

import android.R.attr.category
import android.R.attr.fontWeight
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.screens.CategoryCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun GraficaPay(
    completed: Int,
    total: Int,
    modifier: Modifier = Modifier.size(200.dp),
) {
    val percent = if (total > 0) completed.toFloat() / total else 0f
    val sweepCompleted = 360f * percent

    val strokeWidth = 50f
    val backgroundColor = Color(0xFFE5E9F0)
    val progressColor = Color(0xFFBED2E0)
    val shadowColor = Color.Black.copy(alpha = 0.1f)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = shadowColor,
                radius = size.minDimension / 2,
                center = center
            )
            drawArc(
                color = backgroundColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokeWidth,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepCompleted,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = strokeWidth,
                    cap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            )
            drawCircle(
                color = Color.White,
                radius = (size.minDimension / 2) - strokeWidth,
                center = center
            )
        }

        Text(
            text = "${(percent * 100).toInt()}%",
            color = Color(0xFF405365),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun QuizResultCard(
    name: String,
    score: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDBE7EE)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Text(
                text = "${score}%",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


@Composable
fun ProgresoScreen(
    nav: NavHostController
) {
    val categories =  com.example.manitas.model.getCategories()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    val db = FirebaseFirestore.getInstance()
    var quizAv by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var scores by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }


    LaunchedEffect(userId) {
        if (userId.isNullOrEmpty()) return@LaunchedEffect

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                val list = doc.get("quizAv") as? List<Number>
                quizAv = list?.map { it.toInt() }?.toSet() ?: emptySet()

                val list2 = doc.get("quizScores") as? Map<String, Number>
                scores = list2?.mapValues { it.value.toInt() } ?: emptyMap()
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Row(modifier = Modifier
            .padding(start = 6.dp, top = 40.dp)
            .fillMaxWidth()) {

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Volver",
                tint = Color.Black,
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 13.dp)
                    .clickable { nav.popBackStack() }
            )
            Spacer(Modifier.width(9.dp))

            Text(
                text = "Tu progreso",
                fontSize = 50.sp,
                color = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            GraficaPay(
                total = categories.size,
                completed = quizAv.size,
            )

            Spacer(Modifier.height(30.dp))

            Text(
                text = "${quizAv.size} de ${categories.size} categorías completadas",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            Spacer(Modifier.height(35.dp))

            Text(
                text = "Resultados de quizzes",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                color = Color.Black
            )

            val categoriesWithScore = categories.filter { category ->
                scores.containsKey(category.id.toString())
            }

            LazyColumn {
                items(categoriesWithScore) { category ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                        contentAlignment = Alignment.Center)
                    {
                        val score_current = scores[category.id.toString()] ?: 0
                        QuizResultCard(
                            category.name,
                            score_current
                        )
                    }
                }
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun ProgresoScreenPreview() {
    val nav = rememberNavController()
    ProgresoScreen(nav = nav)
}
