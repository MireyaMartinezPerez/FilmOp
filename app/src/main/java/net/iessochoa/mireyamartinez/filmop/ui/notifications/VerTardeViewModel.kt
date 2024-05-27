package net.iessochoa.mireyamartinez.filmop.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.iessochoa.mireyamartinez.filmop.data.MovieData

class VerTardeViewModel : ViewModel() {

    private val _verTarde = MutableLiveData<MutableList<MovieData>>(mutableListOf())
    val verTarde: LiveData<MutableList<MovieData>> get() = _verTarde

    fun addVerTarde(movie: MovieData) {
        val verTardeList = _verTarde.value ?: mutableListOf()
        if (!verTardeList.contains(movie)) {
            verTardeList.add(movie)
            _verTarde.value = verTardeList
        }
    }
}