package com.example.manitas.screens

import android.R.attr.category
import android.R.attr.text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScopeMarker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun CategoryCard(
    category: Category,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(300.dp)
            .height(130.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxSize()
                .clickable { onItemClick(category) }
        ) {
            Image(
                painter = painterResource(id = category.img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 10.dp,
            modifier = Modifier.padding(0.dp)
        ) {
            Text(
                text = category.name,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun CategoriesScreen(
    categories: List<Category>,
    nav: NavHostController,
    onItemClick: (Category) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Row(modifier = Modifier.padding(start = 20.dp, top = 60.dp).fillMaxWidth()) {

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Volver",
                tint = Color.Black,
                modifier = Modifier.size(40.dp).padding(top = 13.dp).clickable { nav.popBackStack() }
            )
            Spacer(Modifier.width(9.dp))

            Text(
                text = "Categorías",
                fontSize = 50.sp,
                color = Color.Black
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 20.dp, horizontal = 16.dp)
        ) {
            items(categories) { category ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CategoryCard(
                        category = category,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}