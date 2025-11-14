package com.example.manitas.model

class Category (
    val id: Int,
    val name: String,
    val icon: String,
    val img: String,
)

fun getCategories(): List<Category> = listOf(
    Category(1,"Frutas", "🍎", "Foto de fruta"),
    Category(2,"Abecedario","🔤","Foto de abecedario"),
    Category(3,"Colores","🎨","Foto de colores"),
    Category(4,"Animales", "🐶","Foto de animales")

)