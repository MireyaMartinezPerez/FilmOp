package net.iessochoa.mireyamartinez.filmop.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentFavoritosBinding
import net.iessochoa.mireyamartinez.filmop.ui.home.HomeFragmentDirections
import net.iessochoa.mireyamartinez.filmop.ui.utils.FavoritosAdapter

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private val favoritosViewModel: FavoritosViewModel by activityViewModels()

    private val favoritosAdapter by lazy {
        FavoritosAdapter(
            mutableListOf(),
            favoritosViewModel,
            // OnClickListener para eliminar una película de favoritos
            { movie ->
                favoritosViewModel.removeFavorito(movie)
                Toast.makeText(view?.context, "Película eliminada de favoritos", Toast.LENGTH_SHORT).show()
            },
            // OnClickListener para ir a la pantalla de valoraciones
            { movie ->
                navigateToValoracion(movie)
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvPrincipal.layoutManager = LinearLayoutManager(context)
        // Establecer el adaptador en el RecyclerView
        binding.rvPrincipal.adapter = favoritosAdapter

        // Observar cambios en la lista de favoritos
        favoritosViewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            // Actualizar el adaptador con la nueva lista de películas favoritas
            favoritosAdapter.updateMovies(favoritos)
        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun navigateToValoracion(movie: MovieData) {
        val action = FavoritosFragmentDirections.actionNavigationFavoritosToValoracionFragment(movie)
        findNavController().navigate(action)
    }

}