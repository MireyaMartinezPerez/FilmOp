package net.iessochoa.mireyamartinez.filmop.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentVerTardeBinding

class VerTardeFragment : Fragment() {

    private var _binding: FragmentVerTardeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val verTardeViewModel =
            ViewModelProvider(this).get(VerTardeViewModel::class.java)

        _binding = FragmentVerTardeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}