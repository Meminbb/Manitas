package com.example.manitas.model

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.manitas.R

class Category (
    val id: Int,
    val name: String,
    val icon: String,
    @DrawableRes val img: Int,
    quizAvailable: Boolean,
    score : Int = 0
){
    var quizAvailable by mutableStateOf(quizAvailable)
    var score by mutableStateOf(score)
}


fun getCategories(): List<Category> = listOf(
    Category(1,"Frutas", "🍎", R.drawable.frutas, false),
    Category(2,"Abecedario","🔤",R.drawable.abc, false),
    Category(3,"Colores","🎨",R.drawable.colores, false),
    Category(4,"Animales", "🐶",R.drawable.animales, false),
    Category(5,"Saludos", "🐶",R.drawable.saludos, false)


)

fun getNamebyId(id: Int, categories: List<Category>): String {
    return categories.find { it.id == id }?.name ?: "Categoría"
}

fun enableQuiz(id: Int, categories: List<Category>) {
    categories.find { it.id == id }?.quizAvailable = true
}
