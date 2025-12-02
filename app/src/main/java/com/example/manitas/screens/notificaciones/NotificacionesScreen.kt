package com.example.manitas.screens.notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun NotificacionesScreen(
    nav: NavHostController,
    userType: String = "administrador"
) {
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
        Row(verticalAlignment = Alignment.CenterVertically) {

            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }

            Text(
                "Notificaciones",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            if (userType == "administrador") {
                IconButton(onClick = { nav.navigate("notificacionesAdd") }) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar notificación")
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        if (loading) {
            Text("Cargando notificaciones...")
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(lista) { notif ->
                    NotificacionCard(notif)
                }
            }
        }
    }
}

// -------------------------------------------------------------
// TARJETA DE NOTIFICACIÓN (Final)
// -------------------------------------------------------------

@Composable
fun NotificacionCard(n: Notificacion) {

    // --- PARSE FECHA: VIENE COMO "10/12/2025" ---
    val partes = n.date.split("/")
    val dia = partes.getOrNull(0) ?: ""
    val mesNum = partes.getOrNull(1)?.toIntOrNull() ?: 0

    val meses = listOf(
        "Ene","Feb","Mar","Abr","May","Jun",
        "Jul","Ago","Sep","Oct","Nov","Dic"
    )
    val mesTexto = if (mesNum in 1..12) meses[mesNum - 1] else ""

    // --- FORMATO DE HORA ---
    fun formatoHora(hora: String?): String {
        if (hora.isNullOrEmpty()) return ""

        val partesHora = hora.split(":")
        val h = partesHora.getOrNull(0)?.toIntOrNull() ?: return hora
        val m = partesHora.getOrNull(1) ?: "00"

        val suf = if (h < 12) "am" else "pm"
        val h12 = if (h % 12 == 0) 12 else h % 12

        return "$h12:$m$suf"
    }

    val horaInicioFmt = formatoHora(n.horaInicio)
    val horaFinFmt = formatoHora(n.horaFin)

    val horarioTexto =
        if (horaInicioFmt.isNotEmpty() && horaFinFmt.isNotEmpty())
            "$horaInicioFmt - $horaFinFmt"
        else
            ""

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE5F2FF), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

        // --- FECHA IZQUIERDA ---
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            Text(dia, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            Text(mesTexto, fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(Modifier.width(16.dp))

        // --- CONTENIDO DERECHA ---
        Column {
            Text(n.title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(n.message, fontSize = 14.sp)

            if (horarioTexto.isNotEmpty()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    horarioTexto,
                    fontSize = 14.sp,
                    color = Color(0xFF4A4A4A)
                )
            }
        }
    }
}
