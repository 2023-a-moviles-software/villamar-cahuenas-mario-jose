package com.example.clonepinterest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonepinterest.Adapters.imagenesPinterestAdapter
import com.example.clonepinterest.negocio.imagenesPinterest

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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


    fun initRecyclerView(){
        val imagenesPinterest: ArrayList<imagenesPinterest> = ArrayList()
        imagenesPinterest.add(imagenesPinterest("img1"))
        imagenesPinterest.add(imagenesPinterest("img2"))
        imagenesPinterest.add(imagenesPinterest("img3"))
        imagenesPinterest.add(imagenesPinterest("img4"))
        imagenesPinterest.add(imagenesPinterest("img5"))
        imagenesPinterest.add(imagenesPinterest("img6"))
        imagenesPinterest.add(imagenesPinterest("img7"))
        imagenesPinterest.add(imagenesPinterest("img8"))
        imagenesPinterest.add(imagenesPinterest("img9"))


        val recyclerViewImgPinterest = findViewById<RecyclerView>(R.id.rvImgPinterest)
        recyclerViewImgPinterest.layoutManager = androidx.recyclerview.widget.
        GridLayoutManager(this, 2)
        recyclerViewImgPinterest.adapter = imagenesPinterestAdapter(imagenesPinterest)
    }
}