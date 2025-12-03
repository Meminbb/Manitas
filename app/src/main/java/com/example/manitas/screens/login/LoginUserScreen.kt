package com.example.manitas.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.R
import com.example.manitas.datastore.UserDataStore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginUserScreen(nav: NavHostController) {
    val bgColor = Color(194, 216, 229)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {

        Row(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(120.dp)
                )

                val titleFont = FontFamily(Font(R.font.manitas_font))

                Text(
                    text = "MANITAS",
                    fontSize = 55.sp,
                    color = Color.Black,
                    fontFamily = titleFont
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1.25f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
                )
                .background(Color.White)
                .padding(horizontal = 32.dp, vertical = 120.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = { nav.navigate("session") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC1D7E4),
                        contentColor = Color.Black
            )
            ) {
                Text("Iniciar sesión", fontSize = 20.sp)
            }

            OutlinedButton(
                onClick = { nav.navigate("createuser") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top= 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.Black)
            ){
                Text("Registrarse", fontSize = 20.sp)
            }

            TextButton(
                onClick = {
                    val context = nav.context

                    FirebaseAuth.getInstance().signOut()

                    CoroutineScope(Dispatchers.IO).launch {
                        UserDataStore.saveUserId(context, "")
                    }
                    nav.navigate("menu")
                          },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = "Continuar como Invitado",
                    color = Color(0xFF4A80BA)
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun LoginUserScreenPreview() {
    val nav = rememberNavController()
    LoginUserScreen(nav)
}
