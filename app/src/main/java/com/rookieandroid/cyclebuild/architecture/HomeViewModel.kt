package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.rookieandroid.cyclebuild.Bicycle
import com.rookieandroid.cyclebuild.Part
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val bicycles : MutableLiveData<List<Bicycle>> = MutableLiveData<List<Bicycle>>()
    private val suggested : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()
    private val installed : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()
    private val navToggle : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    private val vehicles : ArrayList<Bicycle> = ArrayList()
    private val suggestedParts : ArrayList<Part> = ArrayList()
    private val installedParts : ArrayList<Part> = ArrayList()

    init { navToggle.value = false }

    fun setToggle(flag : Boolean) { navToggle.value = flag }
    fun getToggle() : LiveData<Boolean> { return navToggle }

    fun addBicycle(bike : Bicycle)
    {
        vehicles.add(bike)
        bicycles.value = vehicles
        findSuggestedParts(bike)
        saveUserBike(bike)
    }

    fun getBicycles() : LiveData<List<Bicycle>> { return bicycles }

    fun addSuggestedPart(part : Part)
    {
        if(!suggestedParts.contains(part))
        {
            suggestedParts.add(part)
            suggested.value = suggestedParts
        }

    }

    fun getSuggestedParts() : LiveData<List<Part>> { return suggested }

    fun addInstalledPart(part : Part, bike: Bicycle) { saveUserInstall(part, bike) }

    fun getInstalledParts() : LiveData<List<Part>> { return installed }

    private fun findSuggestedParts(bike : Bicycle)
    {
        viewModelScope.launch {
            dbRef.child("parts").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
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

    fun loadBikes()
    {
        viewModelScope.launch {
            dbRef.child("vehicles").child(auth.currentUser!!.uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    for(snap in snapshot.children)
                    {
                        val bike = snap.getValue(Bicycle::class.java)
                        findSuggestedParts(bike!!)
                        vehicles.add(bike)
                    }

                    bicycles.value = vehicles
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun findInstalledParts(bike: Bicycle)
    {
        viewModelScope.launch {
            dbRef.child("installs").child(auth.currentUser!!.uid).child(bike.name).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val installs = ArrayList<Part>()
                    for(snap in snapshot.children)
                    {
                        val part = snap.getValue(Part::class.java)
                        installs.add(part!!)
                    }

                    installed.value = installs
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    private fun saveUserBike(bike: Bicycle)
    {
        dbRef.child("vehicles").child(auth.currentUser!!.uid).push().setValue(bike)
    }

    private fun saveUserInstall(part: Part, bike: Bicycle)
    {
        dbRef.child("installs").child(auth.currentUser!!.uid).child(bike.name).push().setValue(part)
    }
}