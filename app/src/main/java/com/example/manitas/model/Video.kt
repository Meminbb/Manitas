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


    Video(9, 2, "A", R.raw.letras_a, MediaType.IMAGE),
    Video(10, 2, "B", R.raw.letras_b, MediaType.IMAGE),
    Video(11, 2, "C", R.raw.letras_c, MediaType.IMAGE),
    Video(12, 2, "D", R.raw.letras_d, MediaType.IMAGE),
    Video(13, 2, "E", R.raw.letras_e, MediaType.IMAGE),
    Video(14, 2, "F", R.raw.letras_f, MediaType.IMAGE),
    Video(15, 2, "G", R.raw.letras_g, MediaType.IMAGE),
    Video(16, 2, "H", R.raw.letras_h, MediaType.IMAGE),
    Video(17, 2, "I", R.raw.letras_i, MediaType.IMAGE),

    Video(18, 3, "Abrigo", R.raw.ropa_abrigo, MediaType.VIDEO),

    Video(21, 4, "Número 1", R.raw.numero_1, MediaType.IMAGE),
    Video(22, 4, "Número 2", R.raw.numero_2, MediaType.IMAGE),
    Video(23, 4, "Número 3", R.raw.numero_3, MediaType.IMAGE),

    Video(24, 5, "Hola", R.raw.hola, MediaType.VIDEO),
    Video(25, 5, "Adios", R.raw.adios_web, MediaType.VIDEO),
    Video(25, 5, "Buenos días", R.raw.buenosdias, MediaType.VIDEO),
    Video(26, 5, "Buenas tardes", R.raw.buenastardes, MediaType.VIDEO),
    Video(27, 5, "Buenas noches", R.raw.buenasnoches, MediaType.VIDEO),
    Video(28, 5, "Perdón", R.raw.perdon, MediaType.VIDEO),
    Video(29, 5, "Porfavor", R.raw.porfavor, MediaType.VIDEO),


    Video(30, 6, "Ensalada", R.raw.comida_ensalada, MediaType.VIDEO),
    Video(31, 6, "Galleta", R.raw.comida_galleta, MediaType.VIDEO),
    Video(32, 6, "Hamburguesa", R.raw.comida_hamburguesa, MediaType.VIDEO),
    Video(33, 6, "Huevo", R.raw.comida_huevo, MediaType.VIDEO),
    Video(34, 6, "Pizza", R.raw.comida_pizza, MediaType.VIDEO),
    Video(35, 6, "Pollo", R.raw.comida_pollo, MediaType.VIDEO),
    Video(36, 6, "Pastel", R.raw.comida_pastel, MediaType.VIDEO),


    )
