package com.example.features.manga.home.presentation.Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.features.manga.home.presentation.ViewModel.AuthViewModel
import com.example.features.manga.home.presentation.ViewModel.MangaViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    mangaViewModel: MangaViewModel = viewModel()
) {

    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }


    var mangaIdSeleccionado by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mi Biblioteca Manga",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = { authViewModel.cerrarSesion(navController) }) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión", tint = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título del Manga") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = autor,
            onValueChange = { autor = it },
            label = { Text("Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (titulo.isNotBlank() && autor.isNotBlank()) {
                    if (mangaIdSeleccionado == null) {
                        mangaViewModel.agregarManga(titulo, autor)
                    } else {
                        mangaViewModel.actualizarManga(mangaIdSeleccionado!!, titulo, autor)
                        mangaIdSeleccionado = null
                    }
                    titulo = ""
                    autor = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(if (mangaIdSeleccionado == null) "AGREGAR MANGA" else "GUARDAR CAMBIOS")
        }


        if (mangaIdSeleccionado != null) {
            TextButton(
                onClick = {
                    mangaIdSeleccionado = null
                    titulo = ""
                    autor = ""
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Cancelar Edición", color = Color.Gray)
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        Text(text = "Toca un manga para editarlo:", style = MaterialTheme.typography.labelMedium)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(mangaViewModel.listaMangas) { manga ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {

                            mangaIdSeleccionado = manga.id
                            titulo = manga.titulo
                            autor = manga.autor
                        },
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    ListItem(
                        headlineContent = { Text(manga.titulo) },
                        supportingContent = { Text("Autor: ${manga.autor}") },
                        trailingContent = {
                            IconButton(onClick = { mangaViewModel.eliminarManga(manga.id) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    )
                }
            }
        }
    }
}