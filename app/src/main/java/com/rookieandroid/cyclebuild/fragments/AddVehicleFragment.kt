package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.EmptyDataObserver
import com.rookieandroid.cyclebuild.adapters.PartsAdapter
import com.rookieandroid.cyclebuild.architecture.AddVehicleViewModel
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class AddVehicleFragment : Fragment(R.layout.fragment_vehicle), View.OnClickListener
{
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout
    private lateinit var images : ArrayList<String>
    private lateinit var name : TextView
    private lateinit var partsRecyclerView: RecyclerView
    private lateinit var emptyView : View
    private lateinit var emptyMessage : TextView
    private lateinit var back : ImageView
    private lateinit var addVehicle : Button
    private val parts : ArrayList<Part> = ArrayList()
    private lateinit var vehicle : Bicycle
    private lateinit var snackbar: Snackbar
    private val homeViewModel : HomeViewModel by activityViewModels()
    private val addVehicleViewModel : AddVehicleViewModel by viewModels()
    private val args : VehicleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        vehicle = args.bicycleObject
        images = vehicle.imageUrl//arguments?.getStringArrayList("images") as ArrayList<String>
        snackbar = Snackbar.make(view, "Vehicle Added", Snackbar.LENGTH_SHORT)

        emptyView = view.findViewById(R.id.empty_list)
        emptyMessage = emptyView.findViewById(R.id.empty_list_message)
        emptyMessage.text = getString(R.string.no_parts_installed)

        back = view.findViewById(R.id.back_arrow)
        back.setOnClickListener(this)

        name = view.findViewById(R.id.vehicle_name)
        name.text = vehicle.name//arguments?.getString("name")

        addVehicle = view.findViewById(R.id.add_vehicle)
        addVehicle.setOnClickListener(this)

        viewPager = view.findViewById(R.id.image_list)
        viewPager.adapter = ImagePagerAdapter(this, images)
        tabLayout = view.findViewById(R.id.image_indicator)
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->}.attach()

        view.findViewById<TextView>(R.id.installed_text).text = getString(R.string.compatible_parts)
        partsRecyclerView = view.findViewById(R.id.installed_parts)
        val adapter = PartsAdapter(parts) { position -> onItemClick(position) }
        partsRecyclerView.adapter = adapter
        partsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.registerAdapterDataObserver(EmptyDataObserver(partsRecyclerView, emptyView))

        addVehicleViewModel.getCompatibleParts(vehicle).observe(viewLifecycleOwner) {p ->
            parts.clear()
            parts.addAll(p)
            adapter.notifyDataSetChanged()
        }
    }

    private fun onItemClick(position: Int){}

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            back.id -> { findNavController().navigateUp() }
            addVehicle.id -> {
                snackbar.show()
                homeViewModel.addBicycle(vehicle)
            }
        }
    }

    private inner class ImagePagerAdapter(fragment: Fragment, private val images : ArrayList<String>) : FragmentStateAdapter(fragment)
    {
        override fun getItemCount(): Int = images.size

        override fun createFragment(position: Int): Fragment = ImageSliderFragment(images[position], R.layout.vehicle_image_slider)
    }
}