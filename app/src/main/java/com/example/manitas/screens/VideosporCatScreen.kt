package com.example.manitas.screens

import android.R.attr.contentDescription
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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.model.MediaType
import com.example.manitas.model.Video
import com.example.manitas.model.getCategories
import com.example.manitas.model.getNamebyId
import com.example.manitas.model.getVideos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


@SuppressLint("LocalContextResourcesRead")
@Composable
fun VideosporCatScreen(
    idCategory: Int,
    videos: List<Video>,
    nav: NavHostController? = null,
    selectedVideoId: Int? = null
) {

    val initialIndex = remember(videos, selectedVideoId) {
        selectedVideoId?.let { id ->
            val idx = videos.indexOfFirst { it.id == id }
            if (idx >= 0) idx else 0
        } ?: 0
    }

    var index by remember(videos, selectedVideoId) { mutableStateOf(initialIndex) }
    val current = videos[index]
    val categoryName = getNamebyId(idCategory, getCategories())


    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    val db = FirebaseFirestore.getInstance()

    var favSet by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var quizAv by remember { mutableStateOf<Set<Int>>(emptySet()) }

    LaunchedEffect(userId) {
        if (userId.isNullOrEmpty()) return@LaunchedEffect

        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { doc ->
                val list = doc.get("Fav") as? List<Number>
                val list2 = doc.get("quizAv") as? List<Number>
                favSet = list?.map { it.toInt() }?.toSet() ?: emptySet()
                quizAv = list2?.map { it.toInt() }?.toSet() ?: emptySet()
            }
    }

    val isCurrentFav = favSet.contains(current.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 40.dp,bottom = 2.dp)
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
                text = categoryName, //envez de poner id category llama una función para get el name
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        LazyRow(
            modifier = Modifier

                .padding(top = 25.dp, bottom = 24.dp),
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


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
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

            val context = LocalContext.current

            IconButton( //cuando sea el ultimo no reinicia se cambia el icono y al clikcear manda un mensaje de a haz completado esta leccion se ha desbloqueado el quiz para esta categoria
                onClick = {
                    if (index < videos.lastIndex)
                    {
                        index++
                    } else {
                        val uid = userId ?: return@IconButton
                        val docRef = db.collection("users").document(userId)

                        if (quizAv.contains(current.catId)) {

                            return@IconButton

                        } else {
                            quizAv = quizAv + current.catId
                            docRef.update("quizAv", FieldValue.arrayUnion(current.catId))
                        }

                        Toast.makeText(
                            context,
                            "¡Has completado la lección! Se ha desbloqueado el quiz.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = if (index < videos.lastIndex)
                        Icons.Default.KeyboardArrowRight
                    else
                        Icons.Default.Check,
                    contentDescription = "Siguiente",
                    tint = Color.Black
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp , bottom = 60.dp),
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
            if (!userId.isNullOrEmpty()) {
                IconButton(
                    onClick = {
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