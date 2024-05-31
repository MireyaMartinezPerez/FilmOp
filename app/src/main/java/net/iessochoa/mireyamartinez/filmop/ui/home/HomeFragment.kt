package net.iessochoa.mireyamartinez.filmop.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import net.iessochoa.mireyamartinez.filmop.data.MovieData
import net.iessochoa.mireyamartinez.filmop.databinding.FragmentHomeBinding
import net.iessochoa.mireyamartinez.filmop.ui.utils.MovieAdapter



class HomeFragment : Fragment(), MovieAdapter.MovieAdapterClickInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val DATABASE = "movies"
    private lateinit var dataseRef: DatabaseReference
    private lateinit var adapter: MovieAdapter
    private lateinit var mList: MutableList<MovieData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getDataFromFirebase()
    }

    private fun init() {
        dataseRef = FirebaseDatabase.getInstance().reference
            .child(DATABASE)

        binding.rvPrincipal.setHasFixedSize(true)
        binding.rvPrincipal.layoutManager = LinearLayoutManager(context)

        mList = mutableListOf()
        adapter = MovieAdapter(mList)
        adapter.setListener(this)
        binding.rvPrincipal.adapter = adapter
    }

    private fun getDataFromFirebase() {
        dataseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for (movieSnapshot in snapshot.children) {
                    val peli = movieSnapshot.key?.let {
                        val movie = movieSnapshot.getValue(MovieData::class.java)
                        MovieData(it, movie!!.name, movie.duration, movie.genre, movie.rating, movie.platforms, movie.image, movie.synopsis,movie.opinion)
                    }
                    if (peli != null) {
                        mList.add(peli)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error al cargar la lista de películas", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickedMoviePic(movieData: MovieData) {
        val action = HomeFragmentDirections.actionNavigationHomeToPeliculaDetalleFragment(movieData)
        findNavController().navigate(action)
    }
}