package com.example.manitas.screens.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                .clickable { onItemClick(category) }
        ) {
            Image(
                painter = painterResource(id = category.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // White card with the name + score
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
                    fontSize = 28.sp
                )

                // Show score below name
                Text(
                    text = "Puntaje: ${category.score}",
                    fontSize = 18.sp,
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
    Column(modifier = Modifier.fillMaxSize()) {

        // Header
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

        // List of quiz categories
        LazyColumn {
            items(categories) { category ->

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QuizCategoryCard(
                        category = category,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}
