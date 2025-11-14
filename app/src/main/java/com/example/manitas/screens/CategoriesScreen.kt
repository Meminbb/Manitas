package com.example.manitas.screens

import android.R.attr.category
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.manitas.model.Category
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    val sampleCategories = com.example.manitas.model.getCategories()

    CategoriesScreen(
        categories = sampleCategories,
        onItemClick = {}
    )
}

@Composable
fun CategoriesScreen(
    categories: List<Category>,
    onItemClick: (Category) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
                    .padding(start = 16.dp, top = 24.dp)
                    //aqui va el clickable

            )

            Box(
                modifier = Modifier.padding(top = 36.dp, start = 17.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Categorías",
                    fontSize = 50.sp,
                )
            }
        }


        LazyColumn {
            items(categories) { category ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 35.dp, vertical = 15.dp)
                        .fillMaxWidth()
                        .clickable { onItemClick(category) },
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(222, 242, 255) // amarillo claro
                    )

                ) {
                    Row(
                        modifier = Modifier.padding(30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = category.icon,
                            fontSize = 28.sp,
                            modifier = Modifier.padding(end = 16.dp)
                        )

                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.weight(1f)
                        )

                    }

                }
            }
        }
    }
}