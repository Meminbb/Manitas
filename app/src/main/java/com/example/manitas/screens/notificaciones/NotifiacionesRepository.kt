package com.example.manitas.screens.notificaciones

import com.example.manitas.model.Notificacion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object NotificacionesRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getNotificaciones(): List<Notificacion> {
        return try {
            val snapshot = db.collection("notificaciones")
                .orderBy("date")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                val data = doc.data ?: return@mapNotNull null

                Notificacion(
                    id = doc.id,
                    title = data["title"] as? String ?: "",
                    message = data["message"] as? String ?: "",
                    date = data["date"] as? String ?: "",
                    horaInicio = data["horaInicio"] as? String,
                    horaFin = data["horaFin"] as? String
                )
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addNotificacion(
        title: String,
        message: String,
        date: String,
        horaInicio: String?,
        horaFin: String?
    ) {
        val data = mapOf(
            "title" to title,
            "message" to message,
            "date" to date,
            "horaInicio" to (horaInicio ?: ""),
            "horaFin" to (horaFin ?: "")
        )

        db.collection("notificaciones")
            .add(data)
            .await()
    }
}
