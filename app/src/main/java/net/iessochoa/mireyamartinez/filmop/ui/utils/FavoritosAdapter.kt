package net.iessochoa.mireyamartinez.filmop.ui.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import net.iessochoa.mireyamartinez.filmop.R
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaBinding
import net.iessochoa.mireyamartinez.filmop.databinding.ItemPeliculaFavoritosBinding
import net.iessochoa.mireyamartinez.filmop.ui.favoritos.FavoritosViewModel

class FavoritosAdapter(private var movies: MutableList<MovieData>,
                       private val viewModel: FavoritosViewModel,
                       private val onDeleteItemClicked: (MovieData) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.FavoritosViewHolder>() {

    private lateinit var storageRef: StorageReference

    private val DATABASE = "favorites"
    private val dataseRef = FirebaseDatabase.getInstance().reference
        .child(DATABASE)



    class FavoritosViewHolder(val binding: ItemPeliculaFavoritosBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val binding =
            ItemPeliculaFavoritosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        storageRef = FirebaseStorage.getInstance().getReference()
        return FavoritosViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                binding.tvTitulo.text = this.name
                binding.tvOpinion.text = this.opinion
                binding.rbValoracion.rating = this.rating.toFloat()
                val imageRef = storageRef.child("image").child(this.image)
                Glide.with(binding.cvItem.context)
                    .load(imageRef)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.ivPortada)

                binding.ivDeleteFavorite.setOnClickListener {
                    onDeleteItemClicked(this)
                }
            }
        }
    }


    fun updateMovies(newMovies: List<MovieData>) {
        movies = newMovies.toMutableList()
        dataseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                movies.clear()
                for (movieSnapshot in snapshot.children) {
                    val peli = movieSnapshot.key?.let {
                        val movie = movieSnapshot.getValue(MovieData::class.java)
                        MovieData(
                            it,
                            movie!!.name,
                            movie.duration,
                            movie.genre,
                            movie.rating,
                            movie.platforms,
                            movie.image,
                            movie.synopsis,
                            movie.opinion
                        )
                    }
                    if (peli != null) {
                        movies.add(peli)
                    }
                }
                notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
               // Toast.makeText("Error al cargar la lista de favoritos", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
