package com.example.manitas.screens.login

import android.R.attr.fontWeight
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.manitas.R
import androidx.navigation.NavHostController
import com.example.manitas.navigation.ScreenNames
import com.google.firebase.auth.FirebaseAuth


@Composable
fun OneTimeVideo(
    uri: Uri,
    nav: NavHostController
) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose { player.release() }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                this.player = player
                useController = false
            }
        }
    )

    LaunchedEffect(player) {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    player.pause()

                    val auth = FirebaseAuth.getInstance()
                    val userId = auth.currentUser?.uid

                    if (!userId.isNullOrEmpty()) {
                        nav.navigate(ScreenNames.Menu.route) {
                            popUpTo(ScreenNames.LoginScreen.route) { inclusive = true }
                        }
                    } else {
                        nav.navigate(ScreenNames.LoginUser.route) {
                            popUpTo(ScreenNames.LoginScreen.route) { inclusive = true }
                        }
                    }
                }
            }
        })
    }
}


@Composable
fun LoginScreen(nav: NavHostController) {

    val context = LocalContext.current
    val bgColor = Color(189, 211, 225)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val titleFont = FontFamily(Font(R.font.manitas_font))

            Text(
                text = "MANITAS",
                fontSize = 40.sp,
                color = Color.Black,
                fontFamily = titleFont,
                fontWeight = FontWeight.Thin
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OneTimeVideo(
                uri = Uri.parse("android.resource://${context.packageName}/${R.raw.logo}"),
                nav = nav
            )
        }
    }
}