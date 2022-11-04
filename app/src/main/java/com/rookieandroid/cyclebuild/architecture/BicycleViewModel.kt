package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.rookieandroid.cyclebuild.Bicycle
import kotlinx.coroutines.launch

class BicycleViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val bicycles : MutableLiveData<List<Bicycle>> = MutableLiveData<List<Bicycle>>()

    init { loadBicycles() }

    fun getBicycles() : LiveData<List<Bicycle>> { return bicycles }

    private fun loadBicycles()
    {
        viewModelScope.launch {
            dbRef.child("bicycles").addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot)
                {
                    val bicycleList : ArrayList<Bicycle> = ArrayList()
                    for(snap in snapshot.children)
                    {
                        val bike = snap.getValue(Bicycle::class.java)
                        bicycleList.add(bike!!)
                    }

                    bicycles.value = bicycleList
                }

                override fun onCancelled(error: DatabaseError)
                {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}