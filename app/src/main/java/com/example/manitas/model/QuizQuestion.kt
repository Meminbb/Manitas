package com.example.manitas.model

import com.example.manitas.R


data class QuizQuestion(
    val id: Int,
    val categoryId: Int,
    val name: String,
    val mediaId: Int,
    val mediaType: MediaType,
    val questionText: String,
    val answerOptions: List<String>,
    val correctAnswer: Int
)

fun getQuizQuestionsbyCat(categoryId: Int): List<QuizQuestion> = listOf(
    QuizQuestion(
        id = 1,
        categoryId = 1,
        name = "Manzana",
        mediaId = R.raw.frutas_manzana,
        mediaType = MediaType.VIDEO,
        questionText = "Selecciona el significado correcto",
        answerOptions = listOf("Manzana", "Naranja", "Banano", "Piña"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 2,
        categoryId = 1,
        name = "Naranja",
        mediaId = R.raw.frutas_naranja,
        mediaType = MediaType.VIDEO,
        questionText = "¿Cuál es esta fruta?",
        answerOptions = listOf("Manzana", "Naranja", "Banano", "Piña"),
        correctAnswer = 1
    ),
    QuizQuestion(
        id = 3,
        categoryId = 1,
        name = "Banano",
        mediaId = R.raw.frutas_banano,
        mediaType = MediaType.VIDEO,
        questionText = "¿Cuál es esta fruta?",
        answerOptions = listOf("Manzana", "Naranja", "Banano" , "Piña"),
        correctAnswer = 2
    ),

    QuizQuestion(
        id = 4,
        categoryId = 1,
        name = "Test",
        mediaId = R.raw.frutas_banano,
        mediaType = MediaType.VIDEO,
        questionText = "¿Cuál es esta fruta?",
        answerOptions = listOf("Manzana", "Test", "Banano" , "Piña"),
        correctAnswer = 2
    ),

    QuizQuestion(
        id = 5,
        categoryId = 2,
        name = "A",
        mediaId = R.raw.letras_a,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué letra es esta?",
        answerOptions = listOf("A", "B", "C" , "D"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 6,
        categoryId = 2,
        name = "B",
        mediaId =R.raw.letras_b,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué letra es esta?",
        answerOptions = listOf("A", "B", "C" , "D"),
        correctAnswer = 1
    ),
    QuizQuestion(
        id = 7,
        categoryId = 2,
        name = "C",
        mediaId = R.raw.letras_c,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué letra es esta?",
        answerOptions = listOf("A", "B", "C" , "D"),
        correctAnswer = 2
    ),
    QuizQuestion(
        id = 7,
        categoryId = 2,
        name = "D",
        mediaId = R.raw.letras_d,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué letra es esta?",
        answerOptions = listOf("A", "B", "C" , "D"),
        correctAnswer = 2
),

).filter { it.categoryId == categoryId }
