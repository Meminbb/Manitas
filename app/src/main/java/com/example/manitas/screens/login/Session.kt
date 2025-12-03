package com.example.manitas.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.manitas.R
import com.example.manitas.datastore.UserDataStore
import com.example.manitas.navigation.ScreenNames
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SessionScreen(nav: NavHostController) {
    val bgColor = Color(194, 216, 229)
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(horizontal = 32.dp, vertical = 60.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Inicia Sesión",
                fontSize = 40.sp,
                color = Color.Black,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it.replace(" ","").lowercase() },
                    label = { Text("Correo") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it.replace(" ", "") },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(194, 216, 229)
                    ),
                    onClick = {
                        val trimmedEmail = email.trim()
                        val trimmedPassword = password.trim()

                        if (trimmedEmail.isEmpty() || trimmedPassword.isEmpty()) {
                            errorMessage = "Por favor ingrese correo y contraseña"
                            return@Button
                        }

                        auth.signInWithEmailAndPassword(trimmedEmail, trimmedPassword)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    val userId = auth.currentUser?.uid
                                    if (userId != null) {
                                        val context = nav.context
                                        CoroutineScope(Dispatchers.IO).launch {
                                            UserDataStore.saveUserId(context, userId)
                                        }
                                    }

                                    nav.navigate(ScreenNames.Menu.route) {
                                        popUpTo(ScreenNames.LoginScreen.route) { inclusive = true }
                                    }

                                } else {
                                    errorMessage = "Correo o contraseña incorrecta"
                                }
                            }
                    }
                ) {
                    Text("Iniciar sesión",
                        fontWeight = FontWeight.Bold)
                }


            }
        }
    }
}

fun signInWithEmail(email: String, password: String, auth: FirebaseAuth, nav: NavHostController) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                val userId = user?.uid

                if (userId != null) {
                    nav.navigate("${ScreenNames.Menu.route}/$userId")
                }
            } else {
                val errorMessage = task.exception?.message
                errorMessage?.let { error ->
                    Toast.makeText(nav.context, error, Toast.LENGTH_LONG).show()
                }
            }
        }
}
