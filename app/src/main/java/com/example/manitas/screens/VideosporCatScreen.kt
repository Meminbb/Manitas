package com.example.manitas.screens

import android.R.attr.contentDescription
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.manitas.model.enableQuiz
import com.example.manitas.model.getCategories
import com.example.manitas.model.getNamebyId
import com.example.manitas.model.getVideos


//buscar como get el nombre de la categoria y como modificar true y false el quiz available
@SuppressLint("LocalContextResourcesRead")
@Composable
fun VideosporCatScreen(
    idCategory: Int,
    videos: List<Video>,
    nav: NavHostController? = null
) {
    var index by remember { mutableStateOf(0) } //index del video
    val current = videos[index] //current video
    val categoryName = getNamebyId(idCategory, getCategories())


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
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
                text = categoryName, //envez de poner id category llama una función para get el name
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }

        //lazy row

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
                        contentDescription = "Anterior"
                    )
                }
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

            val context = LocalContext.current

            IconButton( //cuando sea el ultimo no reinicia se cambia el icono y al clikcear manda un mensaje de a haz completado esta leccion se ha desbloqueado el quiz para esta categoria
                onClick = {
                    if (index < videos.lastIndex)
                    {
                        index++
                    } else {
                        enableQuiz(idCategory, getCategories())

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
                        Icons.Default.Check, // icono de completado
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
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "ResID: ${current.resId}",
                    fontSize = 15.sp,
                    color = Color.Gray
                )
            }

            IconButton(onClick = { current.fav = !current.fav }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Fav",
                    tint = if (current.fav) Color.Red else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
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
