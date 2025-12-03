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
)

fun getVideos(): List<Video> = listOf(
    Video(1, 1, "Manzana", R.raw.frutas_manzana, MediaType.VIDEO),
    Video(2, 1, "Naranja", R.raw.frutas_naranja, MediaType.VIDEO),
    Video(3, 1, "Banano", R.raw.frutas_banano, MediaType.VIDEO),


    Video(4, 2, "A", R.raw.letras_a, MediaType.IMAGE),
    Video(5, 2, "B", R.raw.letras_b, MediaType.IMAGE),
    Video(6, 2, "C", R.raw.letras_c, MediaType.IMAGE),

    Video(7, 5, "Hola", R.raw.hola, MediaType.VIDEO),
    Video(8, 5, "Adios", R.raw.adios_web, MediaType.VIDEO),
    Video(9, 5, "Buenos días", R.raw.buenosdias, MediaType.VIDEO),
    Video(10, 5, "Buenas tardes", R.raw.buenastardes, MediaType.VIDEO),
    Video(11, 5, "Buenas noches", R.raw.buenasnoches, MediaType.VIDEO),
    Video(12, 5, "Perdón", R.raw.perdon, MediaType.VIDEO),
    Video(13, 5, "Porfavor", R.raw.porfavor, MediaType.VIDEO),

)
