package com.example.manitas.screens.notificaciones

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.manitas.datastore.UserDataStore
import com.example.manitas.model.Notificacion
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NotificacionesScreen(

    nav: NavHostController,
    userType: String = "administrador"
) {
    val context = LocalContext.current
    var lista by remember { mutableStateOf<List<Notificacion>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    val userId by UserDataStore.getUserId(context).collectAsState(initial = null)

    val isUserAdmin = userId == "bHLf3iXqfSRjKQyixQBi9tgCj8y1"

    LaunchedEffect(Unit) {
        lista = NotificacionesRepository.getNotificaciones()
        loading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 6.dp, top = 40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Volver",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 13.dp)
                        .clickable { nav.popBackStack() }
                )
                Spacer(Modifier.width(9.dp))

                Text(
                    text = "Notificaciones",
                    fontSize = 40.sp,
                    color = Color.Black
                )

            }

            Spacer(Modifier.height(20.dp))

            if (loading) {
                Text("Cargando notificaciones...", color = Color.Black)
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

        if (isUserAdmin) {
            IconButton(
                onClick = { nav.navigate("notificacionesAdd") },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar Notificación",
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun NotificacionCard(n: Notificacion) {

    val partes = n.date.split("/")
    val dia = partes.getOrNull(0) ?: ""
    val mesNum = partes.getOrNull(1)?.toIntOrNull() ?: 0

    val meses = listOf(
        "Ene","Feb","Mar","Abr","May","Jun",
        "Jul","Ago","Sep","Oct","Nov","Dic"
    )
    val mesTexto = if (mesNum in 1..12) meses[mesNum - 1] else ""

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(70.dp)
        ) {
            Text(dia, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(mesTexto, fontSize = 14.sp, color = Color.Gray)
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(n.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(n.message, fontSize = 14.sp, color = Color.Gray)

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
