package com.rookieandroid.cyclebuild.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rookieandroid.cyclebuild.R

class ImageSliderFragment(private val url : String, private val layout: Int) : Fragment()
{
    private lateinit var image : ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        image = view.findViewById(R.id.slider_image)
        val id = resources.getIdentifier(url, "drawable", context?.packageName)
        Glide.with(this).load(id).into(image)
    }
}