package net.iessochoa.mireyamartinez.filmop.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaBinding
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaFavoritosBinding

class FavoritosAdapter(private var movies: MutableList<MovieData>) : RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {
    private var listener: MovieAdapterClickInterface? = null

    fun setListener(listener: MovieAdapterClickInterface) {
        this.listener = listener
    }

    class FavoritosViewHolder(val binding: ItemPeliculaFavoritosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val binding = ItemPeliculaFavoritosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritosViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitulo.text = this.name
                binding.tvGenero.text = this.genre
                binding.rbValoracion.rating = ((this.rating.toFloat()) * 5) / 10
                binding.cvItem.setOnClickListener {
                    listener?.onClickedMoviePic(this)
                }
            }
        }
    }
    fun updateMovies(newMovies: List<MovieData>) {
        movies = newMovies.toMutableList()
        notifyDataSetChanged()
    }

    interface MovieAdapterClickInterface {
        fun onClickedMoviePic(movieData: MovieData)
    }
}