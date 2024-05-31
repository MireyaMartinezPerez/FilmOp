package net.iessochoa.mireyamartinez.filmop

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import net.iessochoa.mireyamartinez.filmop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Configuración de los destinos principales de la navegación
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_favoritos, R.id.navigation_ver_tarde
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Añadido un listener para manejar la selección del botón "Home"
        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    if (navController.currentDestination?.id != R.id.navigation_home) {
                        // Navega a navigation_home sólo si no estás ya en navigation_home
                        navController.navigate(R.id.navigation_home)
                    }
                    true
                }
                R.id.navigation_favoritos -> {
                    if (navController.currentDestination?.id != R.id.navigation_favoritos) {
                        navController.navigate(R.id.navigation_favoritos)
                    }
                    true
                }
                R.id.navigation_ver_tarde -> {
                    if (navController.currentDestination?.id != R.id.navigation_ver_tarde) {
                        navController.navigate(R.id.navigation_ver_tarde)
                    }
                    true
                }
                else -> false
            }
        }
    }

}