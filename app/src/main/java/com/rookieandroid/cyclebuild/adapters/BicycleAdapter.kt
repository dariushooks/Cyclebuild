package com.rookieandroid.cyclebuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.R

class BicycleAdapter(private val bicycles : ArrayList<Bicycle>, private val onItemClick : (position : Int) -> Unit) : RecyclerView.Adapter<BicycleAdapter.BicycleViewHolder>()
{
    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BicycleViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bicycle_list_item, parent, false)
        return BicycleViewHolder(view, bicycles, onItemClick)
    }

    override fun onBindViewHolder(holder: BicycleViewHolder, position: Int)
    {
        holder.bind(position)
    }

    override fun getItemCount(): Int
    {
        return bicycles.size
    }

    class BicycleViewHolder(itemView : View, private val bicycles : ArrayList<Bicycle>, val onItemClick : (position : Int) -> Unit) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        private val name : TextView = itemView.findViewById(R.id.bicycle_name)
        private val image : ImageView = itemView.findViewById(R.id.bicycle_image)

        init { itemView.setOnClickListener(this) }

        fun bind(position : Int)
        {
            name.text = bicycles[position].name
            val id = itemView.resources.getIdentifier(bicycles[position].imageUrl[0], "drawable", itemView.context.packageName)
            Glide.with(itemView).load(id).into(image)
        }
        override fun onClick(v: View?) { onItemClick(adapterPosition) }
    }
}