package com.example.manitas.screens.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.model.FavoriteItem
import com.example.manitas.model.getFavoritos

@Composable
fun VideosFavoritosScreen(
    nav: NavHostController,
    favoritoId: Int,
    items: List<FavoriteItem> = getFavoritos()
) {
    if (items.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay favoritos")
        }
        return
    }

    val startIndex = items.indexOfFirst { it.id == favoritoId }.let { index ->
        if (index == -1) 0 else index
    }
    var index by remember { mutableStateOf(startIndex) }
    val current = items[index]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Favoritos",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    index = if (index > 0) index - 1 else items.lastIndex
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Anterior"
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.7f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0E0E0)
                )
            ) {

            }

            IconButton(
                onClick = {
                    index = if (index < items.lastIndex) index + 1 else 0
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Siguiente"
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = current.title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = current.category,
                    fontSize = 15.sp,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorito",
                tint = Color.Red,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideosFavoritosPreview() {
    val nav = rememberNavController()
    VideosFavoritosScreen(nav = nav, favoritoId = 1)
}
