package com.example.manitas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manitas.model.Video

@Preview(showBackground = true)
@Composable
fun VideosporCatScreenPreview() {
    val sampleVideos = com.example.manitas.model.getVideos()
        .filter { it.catId == 1 } // simula frutas

    VideosporCatScreen(
        idCategory = 1,
        videos = sampleVideos
    )
}

@Composable
fun VideosporCatScreen (
    idCategory: Int,
    videos: List<Video>
){
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
                    text = "Frutas", //aqui va el nombre de la categoria
                    fontSize = 50.sp,
                )
            }
        }

        //poner la imagen con su nombre como tipo carrusel

    }
}