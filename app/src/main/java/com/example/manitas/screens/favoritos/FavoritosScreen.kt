package com.example.manitas.screens.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.manitas.model.FavoriteItem
import com.example.manitas.model.getFavoritos
import com.example.manitas.navigation.ScreenNames

@Composable
fun FavoritosScreen(
    nav: NavHostController,
    items: List<FavoriteItem> = getFavoritos()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Top bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 20.dp)
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Favoritos",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Lista más grande
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { fav ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)   // 🔥 ALTO DEL ITEM
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = fav.title,
                            fontSize = 24.sp,  // 🔥 TEXTO MÁS GRANDE
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = fav.category,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }

                    // Botón grande
                    Box(
                        modifier = Modifier
                            .size(52.dp)  // 🔥 BOTON MÁS GRANDE
                            .clip(CircleShape)
                            .background(Color(0xFFD3E8F5)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                                nav.navigate(
                                    ScreenNames.FavoritoDetalle.createRoute(fav.id)
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Ver favorito",
                                modifier = Modifier.size(36.dp) // 🔥 ICONO MÁS GRANDE
                            )
                        }
                    }
                }

                Divider(
                    thickness = 1.dp,
                    color = Color(0xFFE0E0E0)
                )
            }
        }
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
