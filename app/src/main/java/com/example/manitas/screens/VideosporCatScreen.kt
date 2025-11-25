package com.example.manitas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.manitas.model.Video
<<<<<<< Updated upstream
=======
import com.example.manitas.model.getVideos

@SuppressLint("LocalContextResourcesRead")
@Composable
fun VideosporCatScreen(
    idCategory: Int,
    videos: List<Video>,
    nav: NavHostController? = null
) {
    var index by remember { mutableStateOf(0) }
    val current = videos[index]

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
                text = "Categoría $idCategory",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
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
>>>>>>> Stashed changes

@Preview(showBackground = true)
@Composable
fun VideosporCatScreenPreview() {
    val sampleVideos = com.example.manitas.model.getVideos()
        .filter { it.catId == 1 } // simula frutas

    VideosporCatScreen(
        idCategory = 1,
        videos = sampleVideos
    )
}

@Composable
fun VideosporCatScreen (
    idCategory: Int,
    videos: List<Video>
){
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)) {

            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = null,
                modifier = Modifier.size(60.dp)
                    .padding(start = 16.dp, top = 24.dp)
                //aqui va el clickable

            )

            Box(
                modifier = Modifier.padding(top = 36.dp, start = 17.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Frutas", //aqui va el nombre de la categoria
                    fontSize = 50.sp,
                )
            }
        }

        //poner la imagen con su nombre como tipo carrusel

    }
}