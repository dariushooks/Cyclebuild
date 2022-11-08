package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.firebase.auth.FirebaseAuth
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.BicycleAdapter
import com.rookieandroid.cyclebuild.adapters.EmptyDataObserver
import com.rookieandroid.cyclebuild.adapters.PartsAdapter
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class HomeFragment : Fragment(R.layout.fragment_home), View.OnClickListener
{
    private lateinit var vehicleRecyclerView : RecyclerView
    private lateinit var partRecyclerView: RecyclerView
    private lateinit var emptyVehicleView : View
    private lateinit var emptyVehicleMessage : TextView
    private lateinit var emptyPartView : View
    private lateinit var emptyPartMessage : TextView
    private lateinit var signout : ImageView
    private val bikes : ArrayList<Bicycle> = ArrayList()
    private val parts : ArrayList<Part> = ArrayList()
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        homeViewModel.loadBikes()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        emptyVehicleView = view.findViewById(R.id.empty_vehicles)
        emptyVehicleMessage = emptyVehicleView.findViewById(R.id.empty_list_message)
        emptyVehicleMessage.text = getString(R.string.no_vehicles)

        signout = view.findViewById(R.id.sign_out)
        signout.setOnClickListener(this)

        vehicleRecyclerView = view.findViewById(R.id.your_vehicles)
        val vehicleLayoutManager = LinearLayoutManager(context)
        val vehicleAdapter = BicycleAdapter(bikes) { position -> onVehicleClick(position) }
        vehicleRecyclerView.layoutManager = vehicleLayoutManager
        vehicleRecyclerView.adapter = vehicleAdapter
        vehicleRecyclerView.addItemDecoration(DividerItemDecoration(context, vehicleLayoutManager.orientation))
        vehicleAdapter.registerAdapterDataObserver(EmptyDataObserver(vehicleRecyclerView, emptyVehicleView))

        emptyPartView = view.findViewById(R.id.empty_parts)
        emptyPartMessage = emptyPartView.findViewById(R.id.empty_list_message)
        emptyPartMessage.text = getString(R.string.no_suggested)

        partRecyclerView = view.findViewById(R.id.suggested_parts)
        val partLayoutManager = LinearLayoutManager(context)
        val partAdapter = PartsAdapter(parts) { position -> onPartClick(position) }
        partRecyclerView.layoutManager = partLayoutManager
        partRecyclerView.adapter = partAdapter
        partAdapter.registerAdapterDataObserver(EmptyDataObserver(partRecyclerView, emptyPartView))

        homeViewModel.getBicycles().observe(viewLifecycleOwner){your_bikes ->
            bikes.clear()
            bikes.addAll(your_bikes)
            vehicleAdapter.notifyDataSetChanged()
        }

        homeViewModel.getSuggestedParts().observe(viewLifecycleOwner){s_parts ->
            parts.clear()
            parts.addAll(s_parts)
            partAdapter.notifyDataSetChanged()
        }
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            signout.id -> {
                FirebaseAuth.getInstance().signOut()
                findNavController().navigateUp()
            }
        }
    }

    private fun onVehicleClick(position : Int)
    {
        val bike = bikes[position]
        val action = HomeFragmentDirections.actionFragmentHomeToFragmentVehicle(bike)
        findNavController().navigate(action)
    }

    private fun onPartClick(position: Int)
    {
        val part = parts[position]
        val action = HomeFragmentDirections.actionFragmentHomeToFragmentPart(part)
        findNavController().navigate(action)
    }
}