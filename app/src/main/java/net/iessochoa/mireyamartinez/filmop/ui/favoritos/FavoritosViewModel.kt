package net.iessochoa.mireyamartinez.filmop.ui.favoritos

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class FavoritosViewModel : ViewModel() {

    private val _favoritos = MutableLiveData<MutableList<MovieData>>(mutableListOf())
    val favoritos: LiveData<MutableList<MovieData>> get() = _favoritos

    private val DATABASE = "favorites"
    private val dataseRef = FirebaseDatabase.getInstance().reference
        .child(DATABASE)

    init {
        // Inicializar la carga de favoritos al crear la instancia del ViewModel
        loadFavoritos()
    }
    private fun loadFavoritos() {
        // Escuchar cambios en la base de datos para mantener la lista actualizada
        dataseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favList = mutableListOf<MovieData>()
                for (movieSnapshot in snapshot.children) {
                    val movie = movieSnapshot.getValue(MovieData::class.java)
                    if (movie != null) {
                        favList.add(movie)
                    }
                }
                _favoritos.value = favList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FavoritosViewModel", "Error cargando favoritos", error.toException())
            }
        })
    }
    fun addFavorito(movie: MovieData) {
        val favList = _favoritos.value ?: mutableListOf()
        // Verificar si la película ya está en la

        val existingMovieIndex = favList.indexOfFirst { it.movieId == movie.movieId }
        if (existingMovieIndex != -1) {
            // Si la película ya existe, actualizarla en lugar de agregar una nueva entrada
            favList[existingMovieIndex] = movie
        } else {
            // Si la película no está en la lista, agregarla
            favList.add(movie)
        }

        // Actualizar la lista de favoritos en Firebase
        val movieRef = dataseRef.child(movie.movieId.toString())
        movieRef.setValue(movie)

        // Actualizar el LiveData con la lista de favoritos actualizada
        _favoritos.value = favList

    }
    fun removeFavorito(movie: MovieData) {
        val favList = _favoritos.value ?: mutableListOf()
        if (favList.contains(movie)) {
            val movieRef = dataseRef.child(movie.movieId.toString())
            Log.d("FavoritosViewModel", "Eliminando película: ${movie.name}") // Añadido
            movieRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    favList.remove(movie)
                    _favoritos.value = favList
                    Log.d("FavoritosViewModel", "Película eliminada: ${movie.name}") // Añadido
                } else {
                    Log.e("FavoritosViewModel", "Error deleting movie from Firebase", task.exception)
                }
            }
        }
    }


}