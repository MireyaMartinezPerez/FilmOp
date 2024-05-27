package net.iessochoa.mireyamartinez.filmop.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaBinding
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaFavoritosBinding
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaVerTardeBinding

class VerTardeAdapter(private var movies: MutableList<MovieData>) : RecyclerView.Adapter<VerTardeAdapter.VerTardeViewHolder>() {
    private var listener: MovieAdapterClickInterface? = null

    fun setListener(listener: MovieAdapterClickInterface) {
        this.listener = listener
    }

    class VerTardeViewHolder(val binding: ItemPeliculaVerTardeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerTardeViewHolder {
        val binding = ItemPeliculaVerTardeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerTardeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: VerTardeViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitulo.text = this.name
                binding.tvGenero.text = this.genre
                binding.tvPlataforma.text = this.platforms.toString()
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