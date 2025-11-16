package com.example.manitas.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manitas.navigation.ScreenNames


@Composable
fun MenuScreen(
    onNavigate: (String) -> Unit,
    userName: String = "Karla"
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Text(
            text = "Hola, $userName",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            textAlign = TextAlign.Center
        )


        var q by remember { mutableStateOf("") }
        TextField(
            value = q,
            onValueChange = { q = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            placeholder = { Text("Búsqueda") },
            singleLine = true,
            shape = RoundedCornerShape(24.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFDBE7EE),
                unfocusedContainerColor = Color(0xFFDBE7EE),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )

        Spacer(Modifier.height(10.dp))

        val cardTotalHeight = 340.dp
        val smallCardHeight = 160.dp
        val spacingBetweenSmall = 20.dp
        val notificacionesHeight = 225.dp

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                MenuCard(
                    title = "Diccionario de señas",
                    containerColor = Color(0xFFEDF3F7),
                    modifier = Modifier
                        .weight(1f)
                        .height(cardTotalHeight),
                    onClick = { onNavigate(ScreenNames.Categorias.route) }
                )


                Column(
                    modifier = Modifier
                        .weight(1f)
                        .height(cardTotalHeight),
                    verticalArrangement = Arrangement.spacedBy(spacingBetweenSmall)
                ) {
                    MenuCard(
                        title = "Tu progreso",
                        containerColor = Color(0xFFBED2E0),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(smallCardHeight),
                        onClick = { onNavigate(ScreenNames.Progreso.route) }
                    )

                    MenuCard(
                        title = "Favoritos",
                        containerColor = Color(0xFFDBE7EE),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(smallCardHeight),
                        leading = { Icon(Icons.Default.Favorite, null) },
                        onClick = { onNavigate(ScreenNames.Favoritos.route) }
                    )
                }
            }


            MenuCard(
                title = "Notificaciones",
                containerColor = Color(0xFFA8C3D6),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(notificacionesHeight),
                leading = { Icon(Icons.Default.Notifications, null) },
                onClick = { onNavigate(ScreenNames.Notificaciones.route) }
            )
        }

        Spacer(Modifier.height(6.dp))
    }
}

@Composable
private fun MenuCard(
    title: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFE6E6E6),
    leading: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(22.dp),
                clip = false
            )
            .clip(RoundedCornerShape(22.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (leading != null) leading()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
        }
    }
}