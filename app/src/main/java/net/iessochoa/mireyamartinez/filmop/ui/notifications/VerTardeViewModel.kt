package net.iessochoa.mireyamartinez.filmop.ui.notifications

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
                Log.e("VerTardeViewModel", "Error cargando ver más tarde", error.toException())
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
        val verTardeList = _verTarde.value ?: mutableListOf()
        if (verTardeList.contains(movie)) {
            val movieRef = dataseRef.child(movie.movieId.toString())
            Log.d("VerTardeViewModel", "Eliminando película: ${movie.name}") // Añadido
            movieRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    verTardeList.remove(movie)
                    _verTarde.value = verTardeList
                    Log.d("VerTardeViewModel", "Película eliminada: ${movie.name}") // Añadido
                } else {
                    Log.e("VerTardeViewModel", "Error deleting movie from Firebase", task.exception)
                }
            }
        }
    }
}