package net.iessochoa.mireyamartinez.filmop.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaBinding

class MovieAdapter(private val movies: MutableList<MovieData>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var listener: MovieAdapterClickInterface? = null
    fun setListener(listener: MovieAdapterClickInterface) {
        this.listener = listener
    }

    class MovieViewHolder(val binding: ItemPeliculaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemPeliculaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitulo.text = this.name
                binding.tvGenero.text = this.genre
                binding.cvItem.setOnClickListener {
                    listener?.onClickedMoviePic(this)
                }
            }
        }
    }

    interface MovieAdapterClickInterface {
        fun onClickedMoviePic(movieData: MovieData)
    }

}