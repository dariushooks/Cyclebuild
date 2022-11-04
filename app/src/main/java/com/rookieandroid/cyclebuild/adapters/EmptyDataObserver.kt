package com.rookieandroid.cyclebuild.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EmptyDataObserver(private val recyclerView: RecyclerView, private val view : View) : RecyclerView.AdapterDataObserver()
{
    init { checkIfEmpty() }

    private fun checkIfEmpty()
    {
        val empty = recyclerView.adapter?.itemCount == 0
        if(empty)
        {
            view.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

        else
        {
            view.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

    }

    override fun onChanged()
    {
        super.onChanged()
        checkIfEmpty()
    }
}