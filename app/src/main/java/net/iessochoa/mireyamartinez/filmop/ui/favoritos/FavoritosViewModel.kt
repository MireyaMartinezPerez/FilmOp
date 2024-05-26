package net.iessochoa.mireyamartinez.filmop.ui.favoritos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class FavoritosViewModel : ViewModel() {

    private val _favoritos = MutableLiveData<MutableList<MovieData>>(mutableListOf())
    val favoritos: LiveData<MutableList<MovieData>> get() = _favoritos

    fun addFavorito(movie: MovieData) {
        val favList = _favoritos.value ?: mutableListOf()
        if (!favList.contains(movie)) {
            favList.add(movie)
            _favoritos.value = favList
        }
    }
}