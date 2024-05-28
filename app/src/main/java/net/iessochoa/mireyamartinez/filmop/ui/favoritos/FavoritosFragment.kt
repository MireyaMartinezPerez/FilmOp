package net.iessochoa.mireyamartinez.filmop.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentFavoritosBinding
import net.iessochoa.mireyamartinez.filmop.ui.utils.FavoritosAdapter

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!
    private val favoritosViewModel: FavoritosViewModel by activityViewModels()
    //private val favoritosAdapter by lazy { FavoritosAdapter(mutableListOf()) }
    private val favoritosAdapter by lazy { FavoritosAdapter(mutableListOf(), favoritosViewModel) }

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
        binding.rvPrincipal.adapter = favoritosAdapter

        favoritosViewModel.favoritos.observe(viewLifecycleOwner) { favoritos ->
            favoritosAdapter.updateMovies(favoritos)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}