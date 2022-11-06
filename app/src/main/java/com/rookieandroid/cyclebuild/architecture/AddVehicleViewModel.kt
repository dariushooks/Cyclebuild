package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import kotlinx.coroutines.launch

class AddVehicleViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val compatible : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()

    fun getCompatibleParts(bike: Bicycle) : LiveData<List<Part>>
    {
        loadCompatibleParts(bike)
        return compatible
    }

    private fun loadCompatibleParts(bike : Bicycle)
    {
        viewModelScope.launch {
            dbRef.child("parts").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val compatibleParts : ArrayList<Part> = ArrayList()
                    for(snap in snapshot.children)
                    {
                        val part = snap.getValue(Part::class.java)
                        if(part!!.compatible.contains(bike.name))
                            compatibleParts.add(part)
                    }

                    compatible.value = compatibleParts
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}