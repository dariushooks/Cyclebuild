package com.rookieandroid.cyclebuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R

class PartsAdapter(private val parts : ArrayList<Part>, private val onItemClick : (position : Int) -> Unit) :  RecyclerView.Adapter<PartsAdapter.PartsViewHolder>()
{
    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartsViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.part_list_item, parent, false)
        return PartsViewHolder(view, parts, onItemClick)
    }

    override fun onBindViewHolder(holder: PartsViewHolder, position: Int)
    {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PartsViewHolder, position: Int, payloads: MutableList<Any>)
    {
        holder.bind(position)
    }

    override fun getItemCount(): Int { return parts.size }

    class PartsViewHolder(item : View, private val parts : ArrayList<Part>, private val onItemClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(item), View.OnClickListener
    {
        private val image : ImageView = item.findViewById(R.id.part_image)
        private val name : TextView = item.findViewById(R.id.part_name)

        init { item.setOnClickListener(this) }

        fun bind(position : Int)
        {
            name.text = parts[position].name
            val id = itemView.resources.getIdentifier(parts[position].imageUrl[0], "drawable", itemView.context.packageName)
            Glide.with(itemView).load(id).into(image)
        }

        override fun onClick(v: View?) { onItemClick(adapterPosition) }
    }
}