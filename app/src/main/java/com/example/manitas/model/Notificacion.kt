package com.example.manitas.model

data class Notificacion(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val date: String = "",
    val horaInicio: String? = null,
    val horaFin: String? = null
)
