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
    Video(4, 1, "Sandia", R.raw.frutas_sandia, MediaType.VIDEO),
    Video(5, 1, "Pera", R.raw.frutas_pera, MediaType.VIDEO),
    Video(6, 1, "Tamarindo", R.raw.frutas_tamarindo, MediaType.VIDEO),
    Video(7, 1, "Jamaica", R.raw.frutas_jamaica, MediaType.VIDEO),
    Video(8, 1, "Mango", R.raw.frutas_mango, MediaType.VIDEO),


    Video(1, 2, "A", R.raw.letras_a, MediaType.IMAGE),
    Video(2, 2, "B", R.raw.letras_b, MediaType.IMAGE),
    Video(3, 2, "C", R.raw.letras_c, MediaType.IMAGE),
    Video(4, 2, "D", R.raw.letras_d, MediaType.IMAGE),
    Video(5, 2, "E", R.raw.letras_e, MediaType.IMAGE),
    Video(6, 2, "F", R.raw.letras_f, MediaType.IMAGE),
    Video(7, 2, "G", R.raw.letras_g, MediaType.IMAGE),
    Video(8, 2, "H", R.raw.letras_h, MediaType.IMAGE),
    Video(9, 2, "I", R.raw.letras_i, MediaType.IMAGE),

    Video(1, 3, "Abrigo", R.raw.preguntas_como, MediaType.IMAGE),
    Video(2, 3, "Color Naranja", R.raw.color_naranja, MediaType.IMAGE),
    Video(3, 3, "Color Amarillo", R.raw.color_amarillo, MediaType.IMAGE),

    Video(1, 4, "Número 1", R.raw.numero_1, MediaType.IMAGE),
    Video(2, 4, "Número 2", R.raw.numero_2, MediaType.IMAGE),
    Video(3, 4, "Número 3", R.raw.numero_3, MediaType.IMAGE),

    Video(1, 5, "Hola", R.raw.hola, MediaType.VIDEO),
    Video(2, 5, "Adios", R.raw.adios_web, MediaType.VIDEO),
    Video(3, 5, "Buenos días", R.raw.buenosdias, MediaType.VIDEO),
    Video(4, 5, "Buenas tardes", R.raw.buenastardes, MediaType.VIDEO),
    Video(5, 5, "Buenas noches", R.raw.buenasnoches, MediaType.VIDEO),
    Video(6, 5, "Perdón", R.raw.perdon, MediaType.VIDEO),
    Video(7, 5, "Porfavor", R.raw.porfavor, MediaType.VIDEO),


    Video(1, 6, "Ensalada", R.raw.comida_ensalada, MediaType.VIDEO),
    Video(2, 6, "Galleta", R.raw.comida_galleta, MediaType.VIDEO),
    Video(3, 6, "Hamburguesa", R.raw.comida_hamburguesa, MediaType.VIDEO),
    Video(4, 6, "Huevo", R.raw.comida_huevo, MediaType.VIDEO),
    Video(5, 6, "Pizza", R.raw.comida_pizza, MediaType.VIDEO),
    Video(6, 6, "Pollo", R.raw.comida_pollo, MediaType.VIDEO),
    Video(7, 6, "Pastel", R.raw.comida_pastel, MediaType.VIDEO),


    )
