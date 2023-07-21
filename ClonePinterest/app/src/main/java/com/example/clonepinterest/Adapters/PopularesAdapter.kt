package com.example.clonepinterest.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonepinterest.R
import com.example.clonepinterest.negocio.ideasPinterest
import com.example.clonepinterest.negocio.popularesPinterest

class PopularesAdapter(
    private val popularesPinterest: List<popularesPinterest>,
): RecyclerView.Adapter<PopularesViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PopularesViewHolder(layoutInflater.inflate(R.layout.item_ideas_pinterest, parent, false))
    }


    override fun getItemCount(): Int = popularesPinterest.size


    override fun onBindViewHolder(holder: PopularesViewHolder, position: Int) {
        val resourceId = holder.itemView.context.resources.getIdentifier(popularesPinterest[position].popular
            , "drawable", holder.itemView.context.packageName)
        holder.imagenPopulares.setImageResource(resourceId)
        holder.descripcionPopulares.text = popularesPinterest[position].desc
        Glide.with(holder.itemView.context).load(resourceId).into(holder.imagenPopulares)
    }
}