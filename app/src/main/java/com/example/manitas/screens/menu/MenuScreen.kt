package com.example.manitas.screens.menu

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manitas.navigation.ScreenNames
import com.google.firebase.firestore.FirebaseFirestore

// -------- helpers para leer de /raw --------

@Composable
fun getImageFromRaw(resourceName: String): Bitmap? {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(resourceName, "raw", context.packageName)
    if (resourceId == 0) return null

    val inputStream = context.resources.openRawResource(resourceId)
    return BitmapFactory.decodeStream(inputStream)
}

@Composable
fun getVideoUriFromRaw(resourceName: String): Uri {
    val context = LocalContext.current
    val resourceId = context.resources.getIdentifier(resourceName, "raw", context.packageName)
    return Uri.parse("android.resource://${context.packageName}/$resourceId")
}

fun getRawResourceFiles(): List<String> {
    return listOf(
        "frutas_banana", "frutas_manzana", "frutas_naranja",
        "letras_a", "letras_b", "letras_c", "letras_o"
    )
}

// -------- pantalla de menú --------

@Composable
fun MenuScreen(
    userId: String,
    onNavigate: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    // username desde firestore
    var username by remember { mutableStateOf<String?>(null) }
    val db = FirebaseFirestore.getInstance()

    LaunchedEffect(userId) {
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                username = doc.getString("username") ?: "Usuario"
            }
            .addOnFailureListener {
                username = "Usuario"
            }
    }

    val sendas = getRawResourceFiles()

    val filteredSendas = if (query.isNotEmpty()) {
        sendas.filter { it.contains(query, ignoreCase = true) }
    } else {
        emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // saludo con nombre
        Text(
            text = "Hola, ${username ?: "Cargando..."}",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .padding(top = 40.dp),
            textAlign = TextAlign.Center
        )

        // fila: notificaciones + barra de búsqueda
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFFDBE7EE))
                    .clickable { onNavigate(ScreenNames.Notificaciones.route) }
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones",
                    tint = Color.Black,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar...") },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFDBE7EE),
                    unfocusedContainerColor = Color(0xFFDBE7EE),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(10.dp))

        // resultados de búsqueda de señas
        if (query.isNotEmpty()) {
            if (filteredSendas.isEmpty()) {
                Text(
                    text = "No se encontraron resultados",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(filteredSendas) { seña ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .clickable {
                                    onNavigate(ScreenNames.SeñaDetail.createRoute(seña))
                                }
                        ) {
                            val image = getImageFromRaw(seña)
                            image?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = seña,
                                    modifier = Modifier.size(64.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = seña,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF1A1A1A),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
            }
        }

        // carta grande: diccionario
        MenuCard(
            title = "Diccionario de señas",
            containerColor = Color(0xFFEDF3F7),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            onClick = { onNavigate(ScreenNames.Categorias.route) }
        )

        Spacer(Modifier.height(20.dp))

        // fila: quizzes + progreso/favoritos
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MenuCard(
                title = "Quizzes",
                containerColor = Color(0xFFBED2E0),
                modifier = Modifier
                    .weight(1f)
                    .height(290.dp),
                onClick = { onNavigate(ScreenNames.Quiz.route) }
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MenuCard(
                    title = "Progreso",
                    containerColor = Color(0xFFDBE7EE),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(137.dp),
                    onClick = { onNavigate(ScreenNames.Progreso.route) }
                )

                MenuCard(
                    title = "Favoritos",
                    containerColor = Color(0xFFBED2E0),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(137.dp),
                    leading = { Icon(Icons.Default.Favorite, null) },
                    onClick = { onNavigate(ScreenNames.Favoritos.route) }
                )
            }
        }
    }
}

@Composable
private fun MenuCard(
    title: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFFE6E6E6),
    leading: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(22.dp),
                clip = false
            )
            .clip(RoundedCornerShape(22.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.BottomStart),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (leading != null) leading()
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
        }
    }
}
