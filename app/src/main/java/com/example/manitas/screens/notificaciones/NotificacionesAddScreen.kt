package com.example.manitas.screens.notificaciones

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificacionesAddScreen(
    nav: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        // -----------------------
        // TOP BAR
        // -----------------------
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Agregar notificación",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        AdminNotificacionesUI()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminNotificacionesUI() {

    // Estados del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var agregarFecha by remember { mutableStateOf(false) }
    var fechaTexto by remember { mutableStateOf("Seleccionar fecha") }

    // Estado para mostrar/ocultar DatePicker
    var showDatePicker by remember { mutableStateOf(false) }

    // Estado interno del DatePicker
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val localDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()

                            fechaTexto = "${localDate.dayOfMonth}/${localDate.monthValue}/${localDate.year}"
                        }
                        showDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD), RoundedCornerShape(16.dp))
            .padding(20.dp)
    ) {

        // ---- TÍTULO ----
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // ---- DESCRIPCIÓN ----
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        // ---- CHECKBOX FECHA ----
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = agregarFecha,
                onCheckedChange = { agregarFecha = it }
            )
            Text("Agregar fecha")
        }

        // ---- DATE PICKER REAL ----
        if (agregarFecha) {
            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(fechaTexto)
            }
        }

        Spacer(Modifier.height(24.dp))

        // ---- BOTÓN ENVIAR ----
        Button(
            onClick = { /* acción futura */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("Enviar")
        }
    }
}
