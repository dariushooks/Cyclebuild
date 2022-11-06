package com.rookieandroid.cyclebuild.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rookieandroid.cyclebuild.Part

class VehicleViewModel : ViewModel()
{
    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().reference
    private val installed : MutableLiveData<List<Part>> = MutableLiveData<List<Part>>()


    fun getInstalledParts() : LiveData<List<Part>> { return installed }
}