package com.example.features.manga.home.presentation.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthViewModel : ViewModel() {


    private val auth: FirebaseAuth = Firebase.auth


    var esExitoso by mutableStateOf(false)
        private set


    fun registrarUsuario(email: String, pass: String, context: Context) {
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email.trim(), pass.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    esExitoso = true
                } else {
                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }


    fun loginUsuario(email: String, pass: String, context: Context) {
        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email.trim(), pass.trim())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    esExitoso = true
                } else {
                    Toast.makeText(context, "Acceso denegado: Datos incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
    }


    fun cerrarSesion(navController: NavController) {
        auth.signOut()
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }

    fun resetEstado() {
        esExitoso = false
    }
}