package com.example.manga.data.network



import retrofit2.http.Body
import retrofit2.http.POST


data class RegisterRequest(
    val email: String,
    val pass: String
)

interface ApiService {
    @POST("usuarios")
    suspend fun registrarUsuario(@Body request: RegisterRequest): retrofit2.Response<Unit>
}