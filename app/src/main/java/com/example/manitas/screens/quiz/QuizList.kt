package com.example.manitas.screens.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.model.Category
import com.example.manitas.model.getCategories
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Preview(showBackground = true)
@Composable
fun QuizCategoriesScreenPreview() {
    val sampleQuizCategories = getCategories()
    val nav = rememberNavController()

    QuizCategoriesScreen(
        categories = sampleQuizCategories,
        nav = nav,
        onItemClick = {}
    )
}

@Composable
fun QuizCategoryCard(
    category: Category,
    isEnabled: Boolean,
    score: Int,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(300.dp)
            .height(140.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isEnabled) {
                        Modifier.clickable { onItemClick(category) }
                    } else {
                        Modifier
                    }
                )
        ) {
            Image(
                painter = painterResource(id = category.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Overlay si está bloqueado
        if (!isEnabled) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Termina la lección\npara desbloquear",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Tarjeta con nombre + puntaje
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.padding(0.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)) {

                Text(
                    text = category.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )

                Text(
                    text = "Puntaje: $score",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun QuizCategoriesScreen(
    categories: List<Category>,
    nav: NavHostController,
    onItemClick: (Category) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var unlockedIds by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var scoresByCategory by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }

    LaunchedEffect(userId) {
        if (userId == null) return@LaunchedEffect

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                // quizzes desbloqueados
                val quizAvList = doc.get("quizAv") as? List<Number>
                val ids = quizAvList?.map { it.toInt() }?.toSet() ?: emptySet()
                unlockedIds = ids

                // mapa de scores
                val scoresMap = doc.get("quizScores") as? Map<String, Number>
                scoresByCategory = scoresMap
                    ?.map { (key, value) -> key.toInt() to value.toInt() }
                    ?.toMap()
                    ?: emptyMap()
            }
    }

    Column(modifier = Modifier.fillMaxSize()
        .background(Color.White)) {

        Row(
            modifier = Modifier
                .padding(start = 20.dp, top = 40.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Volver",
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 10.dp)
                    .clickable { nav.popBackStack() }
            )
            Text(
                text = "Quizzes",
                fontSize = 50.sp,
            )
        }

        LazyColumn {
            items(categories) { category ->

                val enabled = unlockedIds.contains(category.id)
                val score = scoresByCategory[category.id] ?: 0

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QuizCategoryCard(
                        category = category,
                        isEnabled = enabled,
                        score = score,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}
