package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.CompatibleAdapter
import com.rookieandroid.cyclebuild.adapters.EmptyDataObserver

class PartFragment : Fragment(R.layout.fragment_part), View.OnClickListener
{
    private lateinit var viewPager : ViewPager2
    private lateinit var tabLayout : TabLayout
    private lateinit var images : ArrayList<String>
    private lateinit var name : TextView
    private lateinit var description : TextView
    private lateinit var bikesRecyclerView: RecyclerView
    private lateinit var emptyView : View
    private lateinit var emptyMessage : TextView
    private lateinit var back : ImageView
    private lateinit var bikes : ArrayList<String>
    private lateinit var part : Part
    private val args : PartFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {

        super.onViewCreated(view, savedInstanceState)
        part = args.partObject
        images = part.imageUrl//arguments?.getStringArrayList("images") as ArrayList<String>
        bikes = part.compatible//arguments?.getStringArrayList("compatible") as ArrayList<String>
        emptyView = view.findViewById(R.id.empty_list)
        emptyMessage = emptyView.findViewById(R.id.empty_list_message)
        emptyMessage.text = getString(R.string.no_compatible_vehicles)
        back = view.findViewById(R.id.back_arrow)
        back.setOnClickListener(this)
        name = view.findViewById(R.id.part_name)
        name.text = part.name//arguments?.getString("name")
        description = view.findViewById(R.id.part_description)
        description.text = part.description//arguments?.getString("description")
        viewPager = view.findViewById(R.id.image_list)
        viewPager.adapter = ImagePagerAdapter(this, images)
        tabLayout = view.findViewById(R.id.image_indicator)
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->}.attach()
        bikesRecyclerView = view.findViewById(R.id.compatibility_list)
        val adapter = CompatibleAdapter(bikes)
        bikesRecyclerView.adapter = adapter
        bikesRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter.registerAdapterDataObserver(EmptyDataObserver(bikesRecyclerView, emptyView))
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            back.id -> {findNavController().navigateUp()}
        }
    }

    private inner class ImagePagerAdapter(fragment: Fragment, private val images : ArrayList<String>) : FragmentStateAdapter(fragment)
    {
        override fun getItemCount(): Int = images.size

        override fun createFragment(position: Int): Fragment = ImageSliderFragment(images[position], R.layout.part_image_slider)
    }
}