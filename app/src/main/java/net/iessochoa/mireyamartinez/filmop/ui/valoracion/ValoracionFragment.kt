package net.iessochoa.mireyamartinez.filmop.ui.valoracion

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import net.iessochoa.mireyamartinez.filmop.R
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentPeliculaDetalleBinding
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentValoracionBinding
import net.iessochoa.mireyamartinez.filmop.ui.favoritos.FavoritosViewModel
import net.iessochoa.mireyamartinez.filmop.ui.home.HomeFragmentDirections
import net.iessochoa.mireyamartinez.filmop.ui.pelicula_detalle.PeliculaDetalleFragmentArgs
import net.iessochoa.mireyamartinez.filmop.ui.pelicula_detalle.PeliculaDetalleFragmentDirections

class ValoracionFragment : Fragment() {
    private var _binding: FragmentValoracionBinding? = null
    val args: ValoracionFragmentArgs by navArgs()
    private val favoritosViewModel: FavoritosViewModel by activityViewModels()
    private lateinit var storageRef: StorageReference


    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ValoracionFragment()
    }

    private val viewModel: ValoracionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentValoracionBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        storageRef = FirebaseStorage.getInstance().getReference()
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar()
        binding.fabGuardar.setOnClickListener {
            args.movieData?.let { movie ->
                movie.rating = binding.rbValoracionPelicula.rating.toDouble()
                movie.opinion = binding.ettvOpinion.text.toString()
                viewModel.updateMovieRating(movie)
                Toast.makeText(
                    context,
                    "Se ha añadido a la lista de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
                favoritosViewModel.addFavorito(movie)
            }
        }
    }

    private fun iniciar(){
        binding.tvTituloPelicula.setText(args.movieData?.name)
        binding.ettvOpinion.setText(args.movieData?.opinion)
        binding.rbValoracionPelicula.rating = (args.movieData.rating.toFloat())
        val imageRef = storageRef.child("image").child(args.movieData.image)
        Glide.with(binding.frameValoracion.context)
            .load(imageRef)
            .error(R.drawable.ic_launcher_background)
            .into(binding.ivPortadaPelicula)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}