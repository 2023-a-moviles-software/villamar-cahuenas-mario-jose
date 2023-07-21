package com.example.clonepinterest.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clonepinterest.R
import com.example.clonepinterest.negocio.ideasPinterest

class IdeasPinterestAdapter(
    private val ideasPinterest: List<ideasPinterest>,
): RecyclerView.Adapter<IdeasPinterestViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IdeasPinterestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return IdeasPinterestViewHolder(layoutInflater.inflate(R.layout.item_ideas_pinterest, parent, false))
    }

    override fun getItemCount(): Int = ideasPinterest.size


    override fun onBindViewHolder(holder: IdeasPinterestViewHolder, position: Int) {
        val resourceId = holder.itemView.context.resources.getIdentifier(ideasPinterest[position].idea
            , "drawable", holder.itemView.context.packageName)
        holder.imagenIdeas.setImageResource(resourceId)
        holder.descripcionIdeas.text = ideasPinterest[position].descripcion
        Glide.with(holder.itemView.context).load(resourceId).into(holder.imagenIdeas)
    }
}