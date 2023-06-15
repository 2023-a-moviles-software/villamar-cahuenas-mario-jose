package com.example.movilessoftware2023a

import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.movilessoftware2023a.databinding.ActivityAacicloVidaBinding

class AACicloVida : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAacicloVidaBinding
    var textoGlobal = ""

    fun mostrarSanckBar(texto:String){
        textoGlobal += texto
        Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_LONG
        )
            .setAction("Action",null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityAacicloVidaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_aaciclo_vida)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
        mostrarSanckBar("onCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSanckBar("onStart")
    }
    override fun onResume() {
        super.onResume()
        mostrarSanckBar("onResume")
    }
    override fun onRestart() {
        super.onRestart()
        mostrarSanckBar("onRestart")
    }
    override fun onPause() {
        super.onPause()
        mostrarSanckBar("onPause")
    }
    override fun onStop() {
        super.onStop()
        mostrarSanckBar("onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        mostrarSanckBar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run{
            //Guardar variables
            //Primitivas
            putString("variableTextoGuardado",textoGlobal)

        }
        super.onSaveInstanceState(outState)

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
            super.onRestoreInstanceState(savedInstanceState)
            val textoRecuperado: String? = savedInstanceState.getString("variableTextoGuardado")
            if(textoRecuperado != null){
                mostrarSanckBar(textoRecuperado)
                textoGlobal= textoRecuperado
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_aaciclo_vida)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}