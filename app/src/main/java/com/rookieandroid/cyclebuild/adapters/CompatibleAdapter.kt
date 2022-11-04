package com.rookieandroid.cyclebuild.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rookieandroid.cyclebuild.R

class CompatibleAdapter(private val bikes : ArrayList<String>) : RecyclerView.Adapter<CompatibleAdapter.CompatibleViewHolder>()
{
    init { setHasStableIds(true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompatibleViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.compatibility_list_item, parent, false)
        return CompatibleViewHolder(view, bikes)
    }

    override fun onBindViewHolder(holder: CompatibleViewHolder, position: Int) { holder.bind(position) }

    override fun getItemCount(): Int = bikes.size

    class CompatibleViewHolder(itemView: View, private val bikes: ArrayList<String>) : RecyclerView.ViewHolder(itemView)
    {
        private val name : TextView = itemView.findViewById(R.id.compatible_name)

        fun bind(position: Int)
        {
            name.text = bikes[position]
        }
    }
}