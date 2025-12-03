package com.example.manitas.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.model.MediaType
import com.example.manitas.model.Video
import com.example.manitas.model.getVideos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun VideosporCatScreen(
    idCategory: Int,
    videos: List<Video>,
    nav: NavHostController? = null,
    selectedVideoId: Int? = null
) {
    if (videos.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay contenidos en esta categoría")
        }
        return
    }

    val initialIndex = remember(videos, selectedVideoId) {
        selectedVideoId?.let { id ->
            val idx = videos.indexOfFirst { it.id == id }
            if (idx >= 0) idx else 0
        } ?: 0
    }

    var index by remember(videos, selectedVideoId) { mutableStateOf(initialIndex) }
    var visitedIds by remember { mutableStateOf(setOf(videos[initialIndex].id)) }

    val current = videos[index]

    // Firebase
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var favSet by remember { mutableStateOf<Set<Int>>(emptySet()) }

    LaunchedEffect(userId) {
        if (userId == null) return@LaunchedEffect

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                val list = doc.get("Fav") as? List<Number>
                favSet = list?.map { it.toInt() }?.toSet() ?: emptySet()
            }
    }

    // Cada vez que cambia el current, lo marcamos como visto
    LaunchedEffect(current.id) {
        visitedIds = visitedIds + current.id
    }

    val isCurrentFav = favSet.contains(current.id)
    val allVisited = visitedIds.size == videos.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        // Barra superior
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            IconButton(onClick = { nav?.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = "Categoría $idCategory",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Contenido principal (video / imagen + flechas)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    index = if (index > 0) index - 1 else videos.lastIndex
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Anterior"
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.7f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE0E0E0)
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (current.type == MediaType.VIDEO) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { context ->
                                android.widget.VideoView(context).apply {
                                    setVideoURI(
                                        "android.resource://${context.packageName}/${current.resId}".toUri()
                                    )
                                    setOnPreparedListener { mp ->
                                        mp.isLooping = true
                                        start()
                                    }
                                }
                            },
                            update = { videoView ->
                                videoView.setVideoURI(
                                    "android.resource://${videoView.context.packageName}/${current.resId}".toUri()
                                )
                                videoView.start()
                            }
                        )
                    }
                    if (current.type == MediaType.IMAGE) {
                        val context = LocalContext.current
                        val inputStream = context.resources.openRawResource(current.resId)
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = current.name,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            IconButton(
                onClick = {
                    index = if (index < videos.lastIndex) index + 1 else 0
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Siguiente"
                )
            }
        }

        // Info + favoritos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = current.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Contenido ${index + 1} de ${videos.size}",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            IconButton(
                onClick = {
                    if (userId == null) return@IconButton

                    val docRef = db.collection("users").document(userId)

                    if (favSet.contains(current.id)) {
                        favSet = favSet - current.id
                        docRef.update("Fav", FieldValue.arrayRemove(current.id))
                    } else {
                        favSet = favSet + current.id
                        docRef.update("Fav", FieldValue.arrayUnion(current.id))
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Fav",
                    tint = if (isCurrentFav) Color.Red else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        // --- BOTÓN TERMINAR LECCIÓN ---
        Spacer(modifier = Modifier.height(8.dp))

        if (allVisited) {
            Button(
                onClick = {
                    if (userId != null) {
                        val docRef = db.collection("users").document(userId)
                        // Guardamos que esta categoría ya tiene quiz disponible
                        docRef.update("quizAv", FieldValue.arrayUnion(idCategory))
                    }
                    // Opcional: regresar o navegar directo al quiz
                    nav?.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Terminar lección", fontSize = 18.sp)
            }
        } else {
            Text(
                text = "Revisa todos los contenidos para terminar la lección.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideosporCatScreenPreview() {
    val sampleVideos = getVideos().filter { it.catId == 1 }
    val nav = rememberNavController()
    VideosporCatScreen(
        idCategory = 1,
        videos = sampleVideos,
        nav = nav
    )
}
