package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.BicycleAdapter
import com.rookieandroid.cyclebuild.architecture.BicycleViewModel

class BicycleFragment : Fragment(R.layout.fragment_bicycle)
{
    private val bicycles : ArrayList<Bicycle> = ArrayList()
    private val bicycleViewModel : BicycleViewModel by viewModels()
    private lateinit var bicycleRecycler : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        bicycleRecycler = view.findViewById(R.id.bicycle_list)
        val layoutManager = LinearLayoutManager(context)
        val adapter = BicycleAdapter(bicycles) { position -> onItemClick(position) }
        bicycleRecycler.layoutManager = layoutManager
        bicycleRecycler.adapter = adapter
        bicycleRecycler.setHasFixedSize(true)
        bicycleRecycler.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        bicycleViewModel.getBicycles().observe(viewLifecycleOwner){ bicycleList ->
            bicycles.clear()
            bicycles.addAll(bicycleList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClick(position : Int)
    {
        val bike = bicycles[position]
        val action = BicycleFragmentDirections.actionFragmentBicycleToFragmentAddVehicle(bike)
        val optionsBuilder = NavOptions.Builder().setLaunchSingleTop(true).build()
        findNavController().navigate(action)
    }
}