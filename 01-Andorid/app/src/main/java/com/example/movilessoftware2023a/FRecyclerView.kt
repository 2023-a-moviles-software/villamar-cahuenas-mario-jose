package com.example.movilessoftware2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FRecyclerView : AppCompatActivity() {
    var totalLikes = 0
    var arreglo = BbaseDeDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frecycler_view)

        inicializarRecyclerView()
    }
    fun inicializarRecyclerView(){
        val reclyclerView = findViewById<RecyclerView>(R.id.rv_entrenadores)
        val adaptador  = FRecyclerViewAdaptadorNombreCedula(
            this,
            arreglo,
            reclyclerView
        )
        reclyclerView.adapter = adaptador
        reclyclerView.itemAnimator = androidx.recyclerview.widget
            .DefaultItemAnimator()
        reclyclerView.layoutManager = androidx.recyclerview.widget
            .LinearLayoutManager(this)
        adaptador.notifyDataSetChanged()
    }

    fun aumentarTotalLikes(){
        totalLikes += 1
        val totalLikesTextView = findViewById<TextView>(R.id.tv_total_likes)
        totalLikesTextView.text = totalLikes.toString()
    }
}