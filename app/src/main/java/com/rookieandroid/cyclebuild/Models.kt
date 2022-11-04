package com.rookieandroid.cyclebuild

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Bicycle(val id : String = "", val name : String = "", val imageUrl : ArrayList<String> = ArrayList())

@Parcelize
data class Part(val id : String = "", val name : String = "", val description : String = "", val compatible : ArrayList<String> = ArrayList(),
                val imageUrl : ArrayList<String> = ArrayList()) : Parcelable
