package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.PartsAdapter
import com.rookieandroid.cyclebuild.architecture.PartViewModel

class PartsFragment : Fragment(R.layout.fragment_parts)
{
    private val parts : ArrayList<Part> = ArrayList()
    private val partViewModel : PartViewModel by viewModels()
    private lateinit var partsRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        partsRecyclerView = view.findViewById(R.id.part_list)
        val layoutManager = LinearLayoutManager(context)
        val adapter = PartsAdapter(parts, {position -> onItemClick(position)})
        partsRecyclerView.layoutManager = layoutManager
        partsRecyclerView.adapter = adapter
        partsRecyclerView.setHasFixedSize(true)
        partViewModel.getParts().observe(viewLifecycleOwner){partList ->
            parts.clear()
            parts.addAll(partList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClick(position : Int)
    {
        val part = parts[position]
        val action = PartsFragmentDirections.actionFragmentPartsToFragmentPart(part)
        findNavController().navigate(action)
    }
}