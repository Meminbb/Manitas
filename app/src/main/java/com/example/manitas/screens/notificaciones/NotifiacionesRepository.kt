package com.example.manitas.screens.notificaciones

import com.example.manitas.model.Notificacion
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object NotificacionesRepository {

    private val db = FirebaseFirestore.getInstance()

    // Obtener notificaciones ordenadas por fecha de creación
    suspend fun getNotificaciones(): List<Notificacion> {
        return try {
            val snapshot = db.collection("notifications")
                .orderBy("createdAt")
                .get()
                .await()

            snapshot.documents.map { doc ->
                Notificacion(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    message = doc.getString("message") ?: "",
                    date = doc.getString("date") ?: ""
                )
            }

        } catch (e: Exception) {
            emptyList()
        }
    }

    // Agregar nueva notificación
    suspend fun addNotificacion(title: String, message: String, date: String) {
        val data = hashMapOf(
            "title" to title,
            "message" to message,
            "date" to date,
            "createdAt" to Timestamp.now()
        )

        db.collection("notifications")
            .add(data)
            .await()
    }
}
