package net.iessochoa.mireyamartinez.filmop.ui.pelicula_detalle

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class PeliculaDetalleViewModel : ViewModel() {
    private val DATABASE = "movies"
    private val databaseRef = FirebaseDatabase.getInstance().reference
        .child(DATABASE)

    // Función para actualizar la valoración en la base de datos
    fun updateMovieRating(movie: MovieData) {
        val movieRef = databaseRef.child(movie.movieId.toString()).child("rating")
        movieRef.setValue(movie.rating)
    }}