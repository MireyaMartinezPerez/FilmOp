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
                // Manejar errores si es necesario
            }
        })
    }
    fun addFavorito(movie: MovieData) {
        val favList = _favoritos.value ?: mutableListOf()
        if (!favList.contains(movie)) {
            favList.add(movie)
            val movieRef = dataseRef.child(movie.movieId.toString())
            movieRef.setValue(movie)
            _favoritos.value = favList
        }
    }
    fun removeFavorito(movie: MovieData) {
        val favList = _favoritos.value ?: mutableListOf()
        if (favList.contains(movie)) {
            favList.remove(movie)
            val movieRef = dataseRef.child(movie.movieId.toString())
            // Eliminar la película de Firebase
            movieRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Actualizar LiveData solo si la eliminación es exitosa
                    _favoritos.value = favList
                } else {
                    // Manejar el error si la eliminación falla
                    Log.e(TAG, "Error al eliminar la película de Firebase: ${task.exception}")
                    // Otras acciones que puedas necesitar, como mostrar un mensaje al usuario
                }
            }
        }
    }

}