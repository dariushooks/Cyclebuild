package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.rookieandroid.cyclebuild.Part
import kotlinx.coroutines.launch

class PartViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val parts : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()

    init { loadParts() }

    fun getParts() : LiveData<List<Part>> { return parts }

    private fun loadParts()
    {
        viewModelScope.launch {
            dbRef.child("parts").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val partList : ArrayList<Part> = ArrayList()
                    for(snap in snapshot.children)
                    {
                        val part = snap.getValue(Part::class.java)
                        partList.add(part!!)
                    }

                    parts.value = partList
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}