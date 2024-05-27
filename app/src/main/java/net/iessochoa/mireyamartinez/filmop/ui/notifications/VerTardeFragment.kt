package net.iessochoa.mireyamartinez.filmop.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentFavoritosBinding
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentVerTardeBinding
import net.iessochoa.mireyamartinez.filmop.ui.favoritos.FavoritosViewModel
import net.iessochoa.mireyamartinez.filmop.ui.utils.FavoritosAdapter
import net.iessochoa.mireyamartinez.filmop.ui.utils.VerTardeAdapter

class VerTardeFragment : Fragment() {

    private var _binding: FragmentVerTardeBinding? = null
    private val binding get() = _binding!!
    private val verTardeViewModel: VerTardeViewModel by activityViewModels()
    private val verTardeAdapter by lazy { VerTardeAdapter(mutableListOf()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVerTardeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPrincipal.layoutManager = LinearLayoutManager(context)
        binding.rvPrincipal.adapter = verTardeAdapter

        verTardeViewModel.verTarde.observe(viewLifecycleOwner) { verTarde1 ->
            verTardeAdapter.updateMovies(verTarde1)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}