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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.manitas.R
import com.example.manitas.navigation.ScreenNames
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SessionScreen(nav: NavHostController) {
    val bgColor = Color(194, 216, 229)
    val auth = FirebaseAuth.getInstance()

    // Variables to hold input values
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
                // Email field
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

                // Password field
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


                // Display the persistent error message if there's any
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Login button
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            signInWithEmail(email, password, auth, nav)
                        } else {
                            errorMessage = "Por favor ingrese correo y contraseña"
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFBAD1E1),
                        contentColor = Color.Black
                    )
                ) {
                    Text("Iniciar Sesión", fontSize = 18.sp)
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
                // If authentication fails, show error
                val errorMessage = task.exception?.message
                // Display the error message in the UI
                errorMessage?.let { error ->
                    // Here we could update a state variable in your parent composable
                    // that holds the error message and shows it persistently.
                    Toast.makeText(nav.context, error, Toast.LENGTH_LONG).show()
                }
            }
        }
}
