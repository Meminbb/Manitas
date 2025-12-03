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
    score : Int = 0,
){
    var quizAvailable by mutableStateOf(quizAvailable)
    var score by mutableStateOf(score)
}


fun getCategories(): List<Category> = listOf(
    Category(1,"Frutas", "🍎", R.drawable.frutas, false,),
    Category(2,"Abecedario","🔤",R.drawable.abc, false),
    Category(6,"Comida", "🐶",R.drawable.comida_imagen, false),
    Category(4,"Numeros", "🐶",R.drawable.numeros, false),
    Category(5,"Saludos", "🐶",R.drawable.saludos, false),
    Category(3,"Ropa","🎨",R.drawable.ropa, false),
    Category(7,"Hogar", "🐶",R.drawable.hogar, false),
    Category(8,"Lugares", "🐶",R.drawable.museo, false),
    Category(9,"Animales", "🐶",R.drawable.animales, false),
    Category(10,"Colores", "🐶",R.drawable.colores, false),


)

fun getNamebyId(id: Int, categories: List<Category>): String { //usar este para progreso
    return categories.find { it.id == id }?.name ?: "Categoría"
}

fun enableQuiz(id: Int, categories: List<Category>) {
    categories.find { it.id == id }?.quizAvailable = true
}
