package com.rookieandroid.cyclebuild.fragments

import android.app.ActionBar.LayoutParams
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import com.rookieandroid.cyclebuild.R
import com.rookieandroid.cyclebuild.adapters.CompatibleVehicleAdapter
import com.rookieandroid.cyclebuild.adapters.EmptyDataObserver
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class AddToVehicleFragment(private val part : Part, private val bikes : ArrayList<Bicycle>) : DialogFragment(R.layout.dialog_add_to_vehicle), View.OnClickListener
{
    private lateinit var close : Button
    private lateinit var image : ImageView
    private lateinit var name : TextView
    private lateinit var emptyView : View
    private lateinit var emptyText : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var group : Group
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)

        close = view.findViewById(R.id.close_dialog)
        close.setOnClickListener(this)

        image = view.findViewById(R.id.part_image)
        val id = resources.getIdentifier(part.imageUrl[0], "drawable", context?.packageName)
        Glide.with(this).load(id).into(image)

        name = view.findViewById(R.id.part_name)
        name.text = part.name

        emptyView = view.findViewById(R.id.compatible_empty)
        emptyText = emptyView.findViewById(R.id.empty_list_message)
        emptyText.text = getString(R.string.no_results)

        recyclerView = view.findViewById(R.id.compatible_list)
        val layoutManager = LinearLayoutManager(context)
        val adapter = CompatibleVehicleAdapter(part, bikes) { position -> onItemClick(position) }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.registerAdapterDataObserver(EmptyDataObserver(recyclerView, emptyView))
        recyclerView.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        group = view.findViewById(R.id.install_success_group)
    }

    private fun onItemClick(position : Int)
    {
        if(part.compatible.contains(bikes[position].name))
        {
            group.visibility = View.VISIBLE
            homeViewModel.addInstalledPart(part, bikes[position])
            findNavController().navigateUp()
        }

        else
            Toast.makeText(context, "Part not compatible, can't be installed", Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?)
    {
        when(v?.id)
        {
            close.id -> {dismiss()}
        }
    }
}