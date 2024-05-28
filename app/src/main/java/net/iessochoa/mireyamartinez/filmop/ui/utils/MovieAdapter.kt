package net.iessochoa.mireyamartinez.filmop.ui.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import net.iessochoa.mireyamartinez.filmop.R
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaBinding

class MovieAdapter(private val movies: MutableList<MovieData>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private lateinit var storageRef: StorageReference

    private var listener: MovieAdapterClickInterface? = null
    fun setListener(listener: MovieAdapterClickInterface) {
        this.listener = listener
    }

    class MovieViewHolder(val binding: ItemPeliculaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemPeliculaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        init()
        return MovieViewHolder(binding)
    }

    private fun init() {
        storageRef = FirebaseStorage.getInstance().getReference()
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitulo.text = this.name
                binding.tvGenero.text = this.genre

                val imageRef = storageRef.child("image").child(this.image)
                Glide.with(binding.cvItem.context)
                    .load(imageRef)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.ivPortada)
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