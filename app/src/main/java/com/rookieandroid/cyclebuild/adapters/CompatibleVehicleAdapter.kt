package com.rookieandroid.cyclebuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R

class CompatibleVehicleAdapter(private val part : Part, private val bikes : ArrayList<Bicycle>, private val onItemClick : (position : Int) -> Unit) :
    RecyclerView.Adapter<CompatibleVehicleAdapter.CompatibleVehicleViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompatibleVehicleViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.compatible_list_item, parent, false)
        return CompatibleVehicleViewHolder(view, part, bikes, onItemClick)
    }

    override fun onBindViewHolder(holder: CompatibleVehicleViewHolder, position: Int) { holder.bind(position) }

    override fun getItemCount(): Int = bikes.size

    class CompatibleVehicleViewHolder(itemView : View, private val part: Part, private val bikes: ArrayList<Bicycle>, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        private val image : ImageView = itemView.findViewById(R.id.bike_image)
        private val name : TextView = itemView.findViewById(R.id.bike_name)
        private val compatible : Group = itemView.findViewById(R.id.group_compatible)
        private val notCompatible : Group = itemView.findViewById(R.id.group_not_compatible)

        init { itemView.setOnClickListener(this) }

        fun bind(position: Int)
        {
            name.text = bikes[position].name
            val id = itemView.resources.getIdentifier(bikes[position].imageUrl[0], "drawable", itemView.context.packageName)
            Glide.with(itemView).load(id).into(image)

            if(part.compatible.contains(bikes[position].name))
            {
                compatible.visibility = View.VISIBLE
            }

            else
            {
                notCompatible.visibility = View.VISIBLE
            }
        }

        override fun onClick(v: View?) { onItemClick(adapterPosition) }
    }
}