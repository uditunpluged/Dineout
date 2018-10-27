package com.phantasmist.dineout.AppModules.home.datamodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class FoodOutletItem @Inject constructor(
        val id:Long?,
        val outletId: String,
        val name: String,
        val address: String,
        val lat: Double?,
        val lng: Double?,
        val category: String,
        val iconUrl: String,
        var disliked: Boolean = false) : Parcelable