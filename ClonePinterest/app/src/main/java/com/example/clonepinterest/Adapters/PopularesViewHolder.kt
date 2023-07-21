package com.example.clonepinterest.Adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonepinterest.R

class PopularesViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val imagenPopulares = view.findViewById<ImageView>(R.id.imgPopulares)
    val descripcionPopulares = view.findViewById<TextView>(R.id.descripcionPopulares)
}