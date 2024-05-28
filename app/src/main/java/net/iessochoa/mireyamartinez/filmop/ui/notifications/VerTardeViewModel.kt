package net.iessochoa.mireyamartinez.filmop.ui.notifications

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class VerTardeViewModel : ViewModel() {

    private val _verTarde = MutableLiveData<MutableList<MovieData>>(mutableListOf())
    val verTarde: LiveData<MutableList<MovieData>> get() = _verTarde
    private val DATABASE = "see_late"
    private val dataseRef = FirebaseDatabase.getInstance().reference
        .child(DATABASE)
    init {
        // Inicializar la carga de favoritos al crear la instancia del ViewModel
        loadVerTarde()
    }

    private fun loadVerTarde() {
        // Escuchar cambios en la base de datos para mantener la lista actualizada
        dataseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val verTardeList = mutableListOf<MovieData>()
                for (movieSnapshot in snapshot.children) {
                    val movie = movieSnapshot.getValue(MovieData::class.java)
                    if (movie != null) {
                        verTardeList.add(movie)
                    }
                }
                _verTarde.value = verTardeList
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar errores si es necesario
            }
        })
    }
    fun addVerTarde(movie: MovieData) {
        val verTardeList = _verTarde.value ?: mutableListOf()
        if (!verTardeList.contains(movie)) {
            verTardeList.add(movie)
            val movieRef = dataseRef.child(movie.movieId.toString())
            movieRef.setValue(movie)
            _verTarde.value = verTardeList
        }
    }
    fun removeVerTarde(movie: MovieData) {
        val favList = _verTarde.value ?: mutableListOf()
        if (favList.contains(movie)) {
            favList.remove(movie)
            val movieRef = dataseRef.child(movie.movieId.toString())
            // Eliminar la película de Firebase
            movieRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Actualizar LiveData solo si la eliminación es exitosa
                    _verTarde.value = favList
                } else {
                    // Manejar el error si la eliminación falla
                    Log.e(ContentValues.TAG, "Error al eliminar la película de Firebase: ${task.exception}")
                    // Otras acciones que puedas necesitar, como mostrar un mensaje al usuario
                }
            }
        }
    }
}