package com.example.manitas.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.manitas.R
import com.example.manitas.datastore.UserDataStore
import com.example.manitas.navigation.ScreenNames
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun CreateUserScreen(nav: NavHostController) {

    val bgColor = Color(194, 216, 229)

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Row(
            modifier = Modifier
                .weight(0.60f)
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
            }
        }

        Column(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                )
                .background(Color.White)
                .padding(horizontal = 32.dp, vertical = 60.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Crear Cuenta",
                fontSize = 40.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de usuario") },
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
                    value = email,
                    onValueChange = { email = it.replace(" ","").lowercase() },
                    label = { Text("Correo electrónico") },
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

                OutlinedTextField(
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it.replace(" ", "") },
                    label = { Text("Repetir contraseña") },
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
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {
                    val trimmedEmail = email.trim()
                    val trimmedUsername = username.trim()

                    if (trimmedUsername.isEmpty() ||
                        trimmedEmail.isEmpty() ||
                        password.isEmpty() ||
                        repeatPassword.isEmpty()
                    ) {
                        errorMessage = "Llena todos los campos."
                        return@Button
                    }

                    if (password != repeatPassword) {
                        errorMessage = "Las contraseñas no coinciden."
                        return@Button
                    }

                    if (password.length < 6) {
                        errorMessage = "La contraseña debe tener mínimo 6 caracteres."
                        return@Button
                    }

                    isLoading = true
                    errorMessage = null


                    auth.createUserWithEmailAndPassword(trimmedEmail, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = task.result?.user?.uid

                                if (userId != null) {
                                    val userData = hashMapOf(
                                        "username" to trimmedUsername,
                                        "email" to trimmedEmail,
                                        "Fav" to emptyList<Int>(),
                                        "quizAv" to emptyList<Int>()
                                    )

                                    db.collection("users")
                                        .document(userId)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            val context = nav.context

                                            CoroutineScope(Dispatchers.IO).launch {
                                                UserDataStore.saveUserId(context, userId)
                                            }
                                            isLoading = false
                                            nav.navigate(ScreenNames.Menu.route)
                                        }
                                        .addOnFailureListener { e ->
                                            isLoading = false
                                            errorMessage = "Error guardando usuario: ${e.message}"
                                        }
                                }
                            } else {
                                isLoading = false
                                errorMessage =
                                    task.exception?.localizedMessage ?: "Error al crear la cuenta."
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBAD1E1),
                    contentColor = Color.Black
                ),
                enabled = !isLoading
            ) {
                Text(
                    if (isLoading) "Creando..." else "Crear Cuenta",
                    fontSize = 18.sp
                )
            }

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMessage!!,
                    color = Color.Red,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateUserScreenPreview() {
    val nav = rememberNavController()
    CreateUserScreen(nav)
}
