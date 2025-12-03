package com.example.manitas.screens.notificaciones

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.AccessTime
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
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun NotificacionesAddScreen(nav: NavHostController) {

    val bgBlue = Color(0xFFDDE9F2)
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    var agregarFecha by remember { mutableStateOf(false) }

    var fecha by remember { mutableStateOf("") }
    var horaInicio by remember { mutableStateOf("") }
    var horaFin by remember { mutableStateOf("") }

    fun openDatePicker() {
        val cal = Calendar.getInstance()
        DatePickerDialog(
            ctx,
            { _, year, month, day ->
                fecha = String.format("%02d/%02d/%04d", day, month + 1, year)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    fun openTimePicker(onTimeSelected: (String) -> Unit) {
        val cal = Calendar.getInstance()
        TimePickerDialog(
            ctx,
            { _, hour, minute ->
                val time = String.format("%02d:%02d", hour, minute)
                onTimeSelected(time)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Agregar\nnotificación",
                fontSize = 28.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(bgBlue, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {

            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = LocalTextStyle.current.copy(color = Color.Black),

            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = agregarFecha,
                    onCheckedChange = { agregarFecha = it }
                )
                Text("Agregar fecha", color = Color.Black)
            }

            if (agregarFecha) {

                Spacer(Modifier.height(10.dp))

                OutlinedTextField(
                    value = fecha,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Fecha") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    trailingIcon = {
                        IconButton(onClick = { openDatePicker() }) {
                            Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                        }
                    }
                )

                Spacer(Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                    OutlinedTextField(
                        value = horaInicio,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora inicio") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        trailingIcon = {
                            IconButton(onClick = { openTimePicker { horaInicio = it } }) {
                                Icon(Icons.Default.AccessTime, contentDescription = "Hora inicio")
                            }
                        }
                    )

                    Spacer(Modifier.width(12.dp))

                    OutlinedTextField(
                        value = horaFin,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Hora fin") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black),
                        trailingIcon = {
                            IconButton(onClick = { openTimePicker { horaFin = it } }) {
                                Icon(Icons.Default.AccessTime, contentDescription = "Hora fin")
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(
                    onClick = {
                        scope.launch {

                            val fechaTexto =
                                if (!agregarFecha || fecha.isBlank()) ""
                                else fecha

                            NotificacionesRepository.addNotificacion(
                                title = titulo,
                                message = descripcion,
                                date = fechaTexto,
                                horaInicio = horaInicio,
                                horaFin = horaFin
                            )

                            nav.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color(0xFFC7D7E6), RoundedCornerShape(50))
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar", tint = Color.Black)
                }
            }
        }
    }
}
