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
    QuizQuestion(
        id = 8,
        categoryId = 3,
        name = "Abrigo",
        mediaId = R.raw.preguntas_como,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué prenda es esta?",
        answerOptions = listOf("Camisa", "Pantalón", "Abrigo" , "Zapatos"),
        correctAnswer = 2
    ),
    QuizQuestion(
        id = 9,
        categoryId = 3,
        name = "Color Naranja",
        mediaId = R.raw.color_naranja,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué color es este?",
        answerOptions = listOf("Rojo", "Azul", "Verde" , "Naranja"),
        correctAnswer = 3
    ),
    QuizQuestion(
        id = 10,
        categoryId = 3,
        name = "Color Amarillo",
        mediaId = R.raw.color_amarillo,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué color es este?",
        answerOptions = listOf("Amarillo", "Azul", "Verde" , "Naranja"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 11,
        categoryId = 4,
        name = "Número 1",
        mediaId = R.raw.numero_1,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué número es este?",
        answerOptions = listOf("1", "2", "3", "4"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 12,
        categoryId = 4,
        name = "Número 2",
        mediaId = R.raw.numero_2,
        mediaType = MediaType.IMAGE,
        questionText = "Selecciona el número correcto",
        answerOptions = listOf("1", "2", "3", "4"),
        correctAnswer = 1
    ),
    QuizQuestion(
        id = 13,
        categoryId = 4,
        name = "Número 3",
        mediaId = R.raw.numero_3,
        mediaType = MediaType.IMAGE,
        questionText = "¿Qué número representa esta seña?",
        answerOptions = listOf("1", "2", "3", "4"),
        correctAnswer = 2
    ),
    QuizQuestion(
        id = 14,
        categoryId = 5,
        name = "Hola",
        mediaId = R.raw.hola,
        mediaType = MediaType.VIDEO,
        questionText = "Selecciona el saludo correcto",
        answerOptions = listOf("Hola", "Adiós", "Buenos días", "Buenas tardes"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 15,
        categoryId = 5,
        name = "Adiós",
        mediaId = R.raw.adios_web,
        mediaType = MediaType.VIDEO,
        questionText = "¿Qué saludo es este?",
        answerOptions = listOf("Hola", "Adiós", "Buenos días", "Buenas tardes"),
        correctAnswer = 1
    ),
    QuizQuestion(
        id = 15,
        categoryId = 5,
        name = "Buenos días",
        mediaId = R.raw.buenosdias,
        mediaType = MediaType.VIDEO,
        questionText = "¿Qué significa este gesto?",
        answerOptions = listOf("Buenas tardes", "Hola", "Buenos días", "Buenas noches"),
        correctAnswer = 2
    ),
    QuizQuestion(
        id = 17,
        categoryId = 5,
        name = "Buenas tardes",
        mediaId = R.raw.buenastardes,
        mediaType = MediaType.VIDEO,
        questionText = "¿Cuál es este saludo?",
        answerOptions = listOf("Buenas noches", "Buenas tardes", "Hola", "Adiós"),
        correctAnswer = 1
    ),

    QuizQuestion(
        id = 18,
        categoryId = 6,
        name = "Ensalada",
        mediaId = R.raw.comida_ensalada,
        mediaType = MediaType.VIDEO,
        questionText = "¿Qué comida representa esta seña?",
        answerOptions = listOf("Ensalada", "Galleta", "Pizza", "Pollo"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 19,
        categoryId = 6,
        name = "Galleta",
        mediaId = R.raw.comida_galleta,
        mediaType = MediaType.VIDEO,
        questionText = "Selecciona el significado correcto",
        answerOptions = listOf("Galleta", "Pastel", "Huevo", "Pollo"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 20,
        categoryId = 6,
        name = "Hamburguesa",
        mediaId = R.raw.comida_hamburguesa,
        mediaType = MediaType.VIDEO,
        questionText = "¿Qué comida es esta?",
        answerOptions = listOf("Hamburguesa", "Pollo", "Ensalada", "Galleta"),
        correctAnswer = 0
    ),
    QuizQuestion(
        id = 21,
        categoryId = 6,
        name = "Huevo",
        mediaId = R.raw.comida_huevo,
        mediaType = MediaType.VIDEO,
        questionText = "Selecciona la opción correcta",
        answerOptions = listOf("Huevo", "Pizza", "Pollo", "Pastel"),
        correctAnswer = 0
    ),



    ).filter { it.categoryId == categoryId }
