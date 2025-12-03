package com.example.manitas.screens.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manitas.model.Video
import com.example.manitas.navigation.ScreenNames



@Composable
fun SearchBarWithResults(
    videos: List<Video>,
    onNavigate: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val filteredVideos = if (query.isNotEmpty()) {
        videos.filter { it.name.contains(query, ignoreCase = true) }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = "Buscar")

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar...") },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFDBE7EE),
                    unfocusedContainerColor = Color(0xFFDBE7EE),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (filteredVideos.isNotEmpty()) {
            Column {
                filteredVideos.forEach { video ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                onNavigate(
                                    ScreenNames.VideosporCat.createRoute(video.catId, video.id)
                                )
                            }
                    ) {
                        Text(
                            text = video.name,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF1A1A1A),
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No se encontraron resultados",
                fontSize = 14.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}