package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val bicycles : MutableLiveData<List<Bicycle>> = MutableLiveData<List<Bicycle>>()
    private val suggested : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()
    private val installed : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()

    private val vehicles : ArrayList<Bicycle> = ArrayList()
    private val suggestedParts : ArrayList<Part> = ArrayList()
    private val installedParts : ArrayList<Part> = ArrayList()

    fun addBicycle(bike : Bicycle)
    {
        vehicles.add(bike)
        bicycles.value = vehicles
        findSuggestedParts(bike)
    }

    fun getBicycles() : LiveData<List<Bicycle>> { return bicycles }

    fun addSuggestedPart(part : Part)
    {
        suggestedParts.add(part)
        suggested.value = suggestedParts
    }

    fun getSuggestedParts() : LiveData<List<Part>> { return suggested }

    fun addInstalledPart(part : Part)
    {
        installedParts.add(part)
        installed.value = installedParts
    }

    fun getInstalledParts() : LiveData<List<Part>> { return installed }

    private fun findSuggestedParts(bike : Bicycle)
    {
        viewModelScope.launch {
            dbRef.child("parts").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val partList : ArrayList<Part> = ArrayList()
                    for(snap in snapshot.children)
                    {
                        val part = snap.getValue(Part::class.java)
                        if(part!!.compatible.contains(bike.name))
                            addSuggestedPart(part)
                    }
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}