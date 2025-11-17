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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.navigation.ScreenNames

@Composable
fun NotificacionesScreen(nav: NavHostController) {

    val userType = "admin"   // ← Cambiar a "usuario" para ocultar el "+"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // -------- TOP BAR --------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Notificaciones",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            // ---- SOLO ADMIN TIENE EL BOTÓN + ----
            if (userType == "admin") {
                IconButton(
                    onClick = { nav.navigate(ScreenNames.NotificacionesAdd.route) }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }

        // -------- LISTA DE NOTIFICACIONES HARDCODEADAS --------
        NotificacionCard(
            dia = "09",
            mes = "OCT",
            titulo = "Taller LSM",
            lugar = "Aulas 4 Salón 402",
            hora = "6:00 pm – 7:30 pm"
        )

        Spacer(Modifier.height(12.dp))

        NotificacionCard(
            dia = "15",
            mes = "OCT",
            titulo = "Clase 1",
            lugar = "Aulas 4 Salón 402",
            hora = "6:00 pm – 7:30 pm"
        )

        Spacer(Modifier.height(12.dp))

        NotificacionCard(
            dia = "20",
            mes = "OCT",
            titulo = "Clase 2",
            lugar = "Aulas 4 Salón 402",
            hora = "6:00 pm – 7:30 pm"
        )

        Spacer(Modifier.height(12.dp))

        InfoCard(
            titulo = "¡Videos nuevos!",
            descripcion = "Se agregaron videos a la categoría “Números”.\nEmpieza a verlos ahora."
        )
    }
}

@Composable
fun NotificacionCard(dia: String, mes: String, titulo: String, lugar: String, hora: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {

        // Fecha
        Column(
            modifier = Modifier
                .width(55.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(dia, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(mes, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.width(16.dp))

        Column {
            Text(titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(lugar, fontSize = 14.sp)
            Text(hora, fontSize = 14.sp)
        }
    }
}

@Composable
fun InfoCard(titulo: String, descripcion: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(titulo, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(descripcion, fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotificacionesScreen() {
    NotificacionesScreen(rememberNavController())
}
