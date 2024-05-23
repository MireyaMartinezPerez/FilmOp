package net.iessochoa.mireyamartinez.filmop.ui.pelicula_detalle

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import net.iessochoa.mireyamartinez.filmop.R
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentPeliculaDetalleBinding

class PeliculaDetalleFragment : Fragment() {
    private var _binding: FragmentPeliculaDetalleBinding? = null
    val args: PeliculaDetalleFragmentArgs by navArgs()
    private val binding get() = _binding!!
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniciar()

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Funcion que inicializa y carga toda la información de pelicula detalle en el fragment
     */
    private fun iniciar(){
        binding.tvTituloPelicula.setText(args.movieData?.name)
        binding.tvGeneroPelicula.setText(args.movieData?.genre)
        binding.tvDuracionPelicula.setText(args.movieData?.duration)
        binding.tvPlataformas.text=(args.movieData?.platforms.toString())
        binding.rbValoracionPelicula.rating = ((args.movieData.rating.toFloat())*5)/10
        //cargaImagen(binding.personajeImage, args.personaje!!.image)

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