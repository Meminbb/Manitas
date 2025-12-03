package com.example.manitas.model
import com.example.manitas.R

data class QuizQuestion(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val mediaId: Int,
    val questionText: String,
    val answerOptions: List<String>,
    val correctAnswer: Int
)

fun getQuizQuestionsbyCat(): List<QuizQuestion> = listOf(
    QuizQuestion(
        id = 1,
        categoryId = 1,
        name = "Manzana",
        mediaId = R.raw.frutas_manzana,
        questionText = "Selecciona el significado correcto",
        answerOptions = listOf("Manzana", "Naranja", "Banano", "Piña"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 2,
        categoryId = 1,
        name = "Naranja",
        mediaId = 2,
        questionText = "¿Cuál es esta fruta?",
        answerOptions = listOf("Manzana", "Naranja", "Banano", "Piña"),
        correctAnswer = 1
    ),
    QuizQuestion(
        id = 3,
        categoryId = 1,
        name = "Banano",
        mediaId = 3,
        questionText = "¿Cuál es esta fruta?",
        answerOptions = listOf("Manzana", "Naranja", "Banano" , "Piña"),
        correctAnswer = 2
    )
)