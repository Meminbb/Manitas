package com.example.manitas.screens.notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.manitas.model.Notificacion
import kotlinx.coroutines.launch

@Composable
fun NotificacionesScreen(
    nav: NavHostController,
    userType: String = "administrador" // cambiar a "administrador" para activar el botón
) {
    val scope = rememberCoroutineScope()

    var lista by remember { mutableStateOf<List<Notificacion>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        lista = NotificacionesRepository.getNotificaciones()
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // Top bar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Text(
                text = "Notificaciones",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            if (userType == "administrador") {
                IconButton(onClick = { nav.navigate("notificacionesAdd") }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar notificación",
                        tint = Color.Black
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (loading) {
            Text("Cargando notificaciones...")
        } else {
            lista.forEach { notif ->
                NotificacionCard(notif)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun NotificacionCard(n: Notificacion) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5F2FF), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        // Fecha
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            val partes = n.date.split(" ")
            Text(partes.getOrNull(0) ?: "", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(partes.getOrNull(1) ?: "", fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(n.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(n.message, fontSize = 14.sp)
            Text("Horario pendiente", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
