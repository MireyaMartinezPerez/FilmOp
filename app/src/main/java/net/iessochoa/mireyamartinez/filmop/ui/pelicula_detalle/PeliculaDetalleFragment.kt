package net.iessochoa.mireyamartinez.filmop.ui.pelicula_detalle

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
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentPeliculaDetalleBinding
import net.iessochoa.mireyamartinez.filmop.ui.favoritos.FavoritosViewModel
import net.iessochoa.mireyamartinez.filmop.ui.notifications.VerTardeViewModel

class PeliculaDetalleFragment : Fragment() {
    private var _binding: FragmentPeliculaDetalleBinding? = null
    val args: PeliculaDetalleFragmentArgs by navArgs()
    private val binding get() = _binding!!
    private val favoritosViewModel: FavoritosViewModel by activityViewModels()
    private val verTardeViewModel: VerTardeViewModel by activityViewModels()
    private lateinit var storageRef: StorageReference
    private var isEditMode: Boolean = false

    companion object {
        fun newInstance() = PeliculaDetalleFragment()
    }

    private val viewModel: PeliculaDetalleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_pelicula_detalle, container, false)
        _binding = FragmentPeliculaDetalleBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        storageRef = FirebaseStorage.getInstance().getReference()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniciar()
        binding.imFavoritos.setOnClickListener {
            args.movieData?.let { movie ->
                favoritosViewModel.addFavorito(movie)
                Toast.makeText(context, "Se ha añadido a la lista de favoritos", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imbVerTarde.setOnClickListener {
            args.movieData?.let { movie ->
                verTardeViewModel.addVerTarde(movie)
                Toast.makeText(context, "Se ha añadido a la lista de ver más tarde", Toast.LENGTH_SHORT).show()
            }
        }
        binding.imbEditar.setOnClickListener{
            // Cambiar el estado de edición

            isEditMode = !isEditMode

            val newIcon = if (isEditMode) R.drawable.ic_save else R.drawable.ic_pen
            binding.imbEditar.setImageResource(newIcon)

            // Cambiar el estado de edición del rating bar
            binding.rbValoracionPelicula.setIsIndicator(!isEditMode)

            // Si se está guardando, actualizar la película en la base de datos
            if (!isEditMode) {
                args.movieData?.let { movie ->
                    movie.rating = (binding.rbValoracionPelicula.rating.toDouble()*10)/5
                    viewModel.updateMovieRating(movie)
                    Toast.makeText(context, "Película guardada", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
     * Funcion que inicializa y carga toda la información de pelicula detalle en el fragment
     */
    private fun iniciar(){
        binding.tvTituloPelicula.setText(args.movieData?.name)
        binding.tvGeneroPelicula.setText(args.movieData?.genre)
        binding.tvDuracionPelicula.setText(args.movieData?.duration)
        binding.tvPlataformas.text=(args.movieData?.platforms.toString())
        binding.rbValoracionPelicula.rating = ((args.movieData.rating.toFloat())*5)/10
        val imageRef = storageRef.child("image").child(args.movieData.image)
        Glide.with(binding.frameDetallePelicula.context)
            .load(imageRef)
            .error(R.drawable.ic_launcher_background)
            .into(binding.ivPortadaPelicula)
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