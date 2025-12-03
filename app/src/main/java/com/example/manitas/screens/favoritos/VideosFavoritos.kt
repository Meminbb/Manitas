package com.example.manitas.screens.favoritos

import android.R
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

@SuppressLint("LocalContextResourcesRead")
@Composable
fun VideosFavoritosScreen(
    nav: NavHostController,
) {
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    val allVideos = remember { getVideos() }

    var favIds by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var isLoading by remember { mutableStateOf(true) }

    var index by remember { mutableStateOf(0) }

    LaunchedEffect(userId) {
        if (userId.isNullOrEmpty()) {
            isLoading = false
            return@LaunchedEffect
        }

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                val list = doc.get("Fav") as? List<Number>
                favIds = list?.map { it.toInt() }?.toSet() ?: emptySet()
                isLoading = false
            }
            .addOnFailureListener {
                favIds = emptySet()
                isLoading = false
            }
    }

    val favVideos: List<Video> = remember(favIds, allVideos) {
        allVideos.filter { favIds.contains(it.id) }
    }

    LaunchedEffect(favVideos.size) {
        if (favVideos.isEmpty()) {
            index = 0
        } else if (index > favVideos.lastIndex) {
            index = favVideos.lastIndex
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {

        Row(modifier = Modifier
            .padding(start = 6.dp, top = 40.dp)
            .fillMaxWidth()) {

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
                text = "Tus favoritos",
                fontSize = 50.sp,
                color = Color.Black
            )
        }

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text("Cargando favoritos...")
                }
            }

            userId.isNullOrEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Inicia sesión para ver tus favoritos.", color = Color.Black)
                }
            }

            favVideos.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no tienes videos en favoritos.", color = Color.Black)
                }
            }

            else -> {
                val current = favVideos[index]
                val isCurrentFav = favIds.contains(current.id)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            index = if (index > 0) index - 1 else favVideos.lastIndex
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
                            .height(260.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(194, 216, 229)
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            if (current.type == MediaType.VIDEO) {
                                AndroidView(
                                    modifier = Modifier.fillMaxSize(),
                                    factory = { ctx ->
                                        android.widget.VideoView(ctx).apply {
                                            setVideoURI(
                                                "android.resource://${ctx.packageName}/${current.resId}".toUri()
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
                                val inputStream =
                                    context.resources.openRawResource(current.resId)
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
                            index = if (index < favVideos.lastIndex) index + 1 else 0
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Siguiente"
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = current.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Black
                        )
                    }

                    IconButton(
                        onClick = {
                            if (userId.isNullOrEmpty()) return@IconButton

                            val docRef = db.collection("users").document(userId)

                            if (favIds.contains(current.id)) {
                                val newSet = favIds - current.id
                                favIds = newSet
                                docRef.update("Fav", FieldValue.arrayRemove(current.id))

                                val newList = allVideos.filter { newSet.contains(it.id) }
                                if (newSet.isEmpty()) {
                                    index = 0
                                } else if (index > newList.lastIndex) {
                                    index = newList.lastIndex
                                }
                            } else {
                                val newSet = favIds + current.id
                                favIds = newSet
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideosFavoritosPreview() {
    val nav = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Text(
            text = "Preview: pantalla de favoritos",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
