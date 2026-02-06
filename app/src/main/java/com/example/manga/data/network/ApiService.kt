package com.example.manga.data.network



import retrofit2.http.Body
import retrofit2.http.POST

// Definimos qué datos necesita el servidor para registrar un usuario
data class RegisterRequest(
    val email: String,
    val pass: String
)

interface ApiService {
    @POST("usuarios") // Esta es la ruta en el servidor
    suspend fun registrarUsuario(@Body request: RegisterRequest): retrofit2.Response<Unit>
}