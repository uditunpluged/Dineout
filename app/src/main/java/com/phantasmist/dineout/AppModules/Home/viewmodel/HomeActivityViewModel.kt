package com.phantasmist.dineout.AppModules.Home.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import javax.inject.Inject

class HomeActivityViewModel @Inject constructor() : ViewModel() {
    var allItems: MutableLiveData<ArrayList<FoodOutletItem>> = MutableLiveData()
    var allMarkers = ArrayList<Marker>()
    lateinit var mGoogleMap: GoogleMap

    init {
        allItems.value = ArrayList()
    }

}