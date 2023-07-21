package com.example.clonepinterest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonepinterest.Adapters.IdeasPinterestAdapter
import com.example.clonepinterest.negocio.ideasPinterest

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initRecyclerView()

        val btnHome = findViewById<ImageView>(R.id.btnHome)
        val btnSearch = findViewById<ImageView>(R.id.btnSearch)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        val ideasPinterest: ArrayList<ideasPinterest> = ArrayList()
        ideasPinterest.add(ideasPinterest("idea1", "Logotipo moderno"))
        ideasPinterest.add(ideasPinterest("idea2", "Carteles de lucha libre"))
        ideasPinterest.add(ideasPinterest("idea3", "Arte de cuento de adas"))
        ideasPinterest.add(ideasPinterest("idea4", "Tatujaes de alas"))

        val recyclerViewIdeasPinterest = findViewById<RecyclerView>(R.id.rvIdeasPinterest)
        recyclerViewIdeasPinterest.layoutManager = androidx.recyclerview.widget.
        GridLayoutManager(this, 2)
        recyclerViewIdeasPinterest.adapter = IdeasPinterestAdapter(ideasPinterest)

        val popularesPinterest: ArrayList<ideasPinterest> = ArrayList()
        popularesPinterest.add(ideasPinterest("popular1", "Spiderman Wallpaper"))
        popularesPinterest.add(ideasPinterest("popular2", "Fondo de pantalla de anime"))
        popularesPinterest.add(ideasPinterest("popular3", "Dibuujos de pixeles"))
        popularesPinterest.add(ideasPinterest("popular4", "Imagenes divertidas para whatsapp"))
        popularesPinterest.add(ideasPinterest("popular5", "Posters art deco"))
        popularesPinterest.add(ideasPinterest("popular6", "Bocetos artisiticos"))
        popularesPinterest.add(ideasPinterest("popular7", "Dragon ball"))
        popularesPinterest.add(ideasPinterest("popular8", "Kimetzu no yaiba"))

        val recyclerViewPopularesPinterest = findViewById<RecyclerView>(R.id.rvPopularesPinterest)
        recyclerViewPopularesPinterest.layoutManager = androidx.recyclerview.widget.
                GridLayoutManager(this, 2)
        recyclerViewPopularesPinterest.adapter = IdeasPinterestAdapter(popularesPinterest)


    }
}