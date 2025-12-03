package com.example.manitas.screens

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.model.MediaType
import com.example.manitas.model.Video
import com.example.manitas.model.getCategories
import com.example.manitas.model.getNamebyId
import com.example.manitas.model.getVideos
import com.google.firebase.auth.FirebaseAuth
#include com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("LocalContextResourcesRead")
@Composable
fun VideosporCatScreen(
    idCategory: Int,
    videos: List<Video>,
    nav: NavHostController? = null,
    selectedVideoId: Int? = null
) {
    if (videos.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay contenidos en esta categoría")
        }
        return
    }

    // PRESELECCIÓN
    val initialIndex = remember(videos, selectedVideoId) {
        selectedVideoId?.let { id ->
            videos.indexOfFirst { it.id == id }.takeIf { it >= 0 } ?: 0
        } ?: 0
    }

    var index by remember { mutableStateOf(initialIndex) }
    var visitedIds by remember { mutableStateOf(setOf(videos[initialIndex].id)) }

    val current = videos[index]
    val categoryName = getNamebyId(idCategory, getCategories())

    // Firebase
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var favSet by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var quizAv by remember { mutableStateOf<Set<Int>>(emptySet()) }

    // Obtener favoritos y quizzes desbloqueados
    LaunchedEffect(userId) {
        if (userId.isNullOrEmpty()) return@LaunchedEffect

        db.collection("users").document(userId).get()
            .addOnSuccessListener { doc ->
                val favList = doc.get("Fav") as? List<Number>
                val quizList = doc.get("quizAv") as? List<Number>

                favSet = favList?.map { it.toInt() }?.toSet() ?: emptySet()
                quizAv = quizList?.map { it.toInt() }?.toSet() ?: emptySet()
            }
    }

    // Marcar videos vistos
    LaunchedEffect(current.id) {
        visitedIds = visitedIds + current.id
    }

    val isCurrentFav = favSet.contains(current.id)
    val allVisited = visitedIds.size == videos.size
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        // TOP BAR
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 40.dp, bottom = 2.dp)
        ) {
            IconButton(onClick = { nav?.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Volver",
                    tint = Color.Black
                )
            }

            Spacer(Modifier.width(8.dp))

            Text(
                text = categoryName,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // TABS DE VIDEO / CONTENIDO
        LazyRow(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 50.dp, bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(videos) { idx, video ->
                val isSelected = idx == index

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40))
                        .background(
                            if (isSelected) Color(0xFFBED2E0)
                            else Color(0xFFEFF3F7)
                        )
                        .clickable { index = idx }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = video.name,
                        fontSize = 14.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = Color.Black
                    )
                }
            }
        }

        // CONTENIDO CENTRAL (VIDEO / IMAGEN)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {

            // Izquierda
            IconButton(
                onClick = { index-- },
                enabled = index > 0,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                if (index > 0) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Anterior",
                        tint = Color.Black
                    )
                }
            }

            // Video/Image Card
            Card(
                modifier = Modifier.fillMaxWidth(0.75f).height(260.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(194, 216, 229))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (current.type == MediaType.VIDEO) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { context ->
                                VideoView(context).apply {
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
                    } else {
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

            // Derecha (siguiente o terminar lección)
            IconButton(
                onClick = {
                    if (index < videos.lastIndex) {
                        index++
                    } else {
                        // TERMINÓ LA LECCIÓN
                        if (!quizAv.contains(idCategory)) {
                            db.collection("users").document(userId!!)
                                .update("quizAv", FieldValue.arrayUnion(idCategory))

                            Toast.makeText(
                                context,
                                "¡Has completado la lección! Quiz desbloqueado.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (index < videos.lastIndex)
                        Icons.Default.KeyboardArrowRight else Icons.Default.Check,
                    contentDescription = "Siguiente",
                    tint = Color.Black
                )
            }
        }

        // INFO Y FAVORITOS
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = current.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "Contenido ${index + 1} de ${videos.size}",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            if (!userId.isNullOrEmpty()) {
                IconButton(
                    onClick = {
                        val docRef = db.collection("users").document(userId)
                        if (isCurrentFav) {
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
        }

        // BOTÓN TERMINAR LECCIÓN
        Spacer(modifier = Modifier.height(8.dp))

        if (allVisited) {
            Button(
                onClick = {
                    if (userId != null) {
                        db.collection("users").document(userId)
                            .update("quizAv", FieldValue.arrayUnion(idCategory))
                    }
                    nav?.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Terminar lección", fontSize = 18.sp)
            }
        } else {
            Text(
                text = "Revisa todos los contenidos para terminar la lección.",
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
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
