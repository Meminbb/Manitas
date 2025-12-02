package com.example.manitas.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.manitas.R

enum class MediaType {
    VIDEO,
    IMAGE
}

class Video(
    val id: Int,
    val catId: Int,
    val name: String,
    val resId: Int,
    val type: MediaType,
    fav: Boolean,

){
    var fav by mutableStateOf(fav)
}

fun getVideos(): List<Video> = listOf(
    Video(1, 1, "Manzana", R.raw.frutas_manzana, MediaType.VIDEO, false),
    Video(2, 1, "Naranja", R.raw.frutas_naranja, MediaType.VIDEO, false),
    Video(3, 1, "Banano", R.raw.frutas_banano, MediaType.VIDEO, false),

    Video(4, 2, "A", R.raw.letras_a, MediaType.IMAGE, false),
    Video(5, 2, "B", R.raw.letras_b, MediaType.IMAGE, false),
    Video(6, 2, "C", R.raw.letras_c, MediaType.IMAGE, false),
)
