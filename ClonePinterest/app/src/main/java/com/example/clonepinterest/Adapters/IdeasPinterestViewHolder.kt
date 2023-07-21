package com.example.clonepinterest.Adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clonepinterest.R

class IdeasPinterestViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val imagenIdeas = view.findViewById<ImageView>(R.id.imageIdeas)
    val descripcionIdeas = view.findViewById<TextView>(R.id.descripcionIdeas)
}