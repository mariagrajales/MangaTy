package com.example.features.manga.home.presentation.ViewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


data class Manga(
    val id: String = "",
    val titulo: String = "",
    val autor: String = ""
)

class MangaViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()


    var listaMangas = mutableStateListOf<Manga>()

    init {
        obtenerMangas()
    }


    fun agregarManga(titulo: String, autor: String) {
        val nuevoManga = hashMapOf(
            "titulo" to titulo,
            "autor" to autor
        )
        db.collection("mangas").add(nuevoManga).addOnSuccessListener {
            obtenerMangas()
        }
    }

    fun obtenerMangas() {
        db.collection("mangas").addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                listaMangas.clear()
                for (doc in snapshot.documents) {
                    val manga = Manga(
                        id = doc.id,
                        titulo = doc.getString("titulo") ?: "",
                        autor = doc.getString("autor") ?: ""
                    )
                    listaMangas.add(manga)
                }
            }
        }
    }

    fun actualizarManga(id: String, nuevoTitulo: String, nuevoAutor: String) {
        val datosActualizados = mapOf(
            "titulo" to nuevoTitulo,
            "autor" to nuevoAutor
        )
        db.collection("mangas").document(id)
            .update(datosActualizados)
            .addOnSuccessListener {
                obtenerMangas()
            }
    }

    fun eliminarManga(id: String) {
        db.collection("mangas").document(id).delete()
            .addOnSuccessListener {
                obtenerMangas()
            }
    }
}