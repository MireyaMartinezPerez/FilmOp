package net.iessochoa.mireyamartinez.filmop.ui.favoritos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentFavoritosBinding

class FavoritosFragment : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritosViewModel =
            ViewModelProvider(this).get(FavoritosViewModel::class.java)

        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}