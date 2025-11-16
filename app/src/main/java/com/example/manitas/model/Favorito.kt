package com.example.manitas.model

data class FavoriteItem(
    val id: Int,
    val title: String,
    val category: String,
    val img: String = ""
)


fun getFavoritos(): List<FavoriteItem> = listOf(
    FavoriteItem(1, "Letra M", "Alfabeto"),
    FavoriteItem(2, "Manzana", "Frutas"),
    FavoriteItem(3, "Perro", "Animales")
)
