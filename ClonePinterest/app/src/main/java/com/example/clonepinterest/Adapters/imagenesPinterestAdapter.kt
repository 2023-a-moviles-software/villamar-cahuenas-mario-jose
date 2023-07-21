package com.example.clonepinterest.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonepinterest.R
import com.example.clonepinterest.negocio.imagenesPinterest

class imagenesPinterestAdapter(
    private val imagenesPinterest: List<imagenesPinterest>
) : RecyclerView.Adapter<imagenesPinterestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imagenesPinterestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return imagenesPinterestViewHolder(layoutInflater.inflate(R.layout.item_imagen_pinterest, parent, false))
    }

    override fun getItemCount(): Int  = imagenesPinterest.size

    override fun onBindViewHolder(holder: imagenesPinterestViewHolder, position: Int) {
        val drawableResourceId = holder.itemView.context.resources.getIdentifier(imagenesPinterest[position].imagen
            , "drawable", holder.itemView.context.packageName)
        holder.imagenPinterest.setImageResource(drawableResourceId)
        Glide.with(holder.itemView.context).load(drawableResourceId).into(holder.imagenPinterest)
    }


}