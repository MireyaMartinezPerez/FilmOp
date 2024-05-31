package net.iessochoa.mireyamartinez.filmop.ui.valoracion

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class ValoracionViewModel : ViewModel() {
    private val DATABASE = "movies"
    private val databaseRef = FirebaseDatabase.getInstance().reference
        .child(DATABASE)

    // Función para actualizar la valoración en la base de datos
    fun updateMovieRating(movie: MovieData) {
        val movieRef = databaseRef.child(movie.movieId.toString())
        // Actualizar la valoración
        movieRef.child("rating").setValue(movie.rating)
        // Actualizar la opinión
        movieRef.child("opinion").setValue(movie.opinion)
    }
}