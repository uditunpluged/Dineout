package com.phantasmist.dineout.AppModules.FoodOutletDetails.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.phantasmist.dineout.AppModules.FoodOutletDetails.datamodel.FoodOutletDetailsDataModel
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import javax.inject.Inject

/**
 *ViewModel class to manage UI - related data in a lifecycle conscious way
 * This class also allows data to survive configuration changes like screen rotations
 * */

class FoodOutletDetailViewModel @Inject constructor() : ViewModel() {
    var foodOutletBasicDetails: MutableLiveData<FoodOutletItem> = MutableLiveData()
    var foodOutletExtraDetails: MutableLiveData<FoodOutletDetailsDataModel> = MutableLiveData()
    var apiCallHasBeenMade:MutableLiveData<Boolean> = MutableLiveData()

    init {
        apiCallHasBeenMade.value=false
    }

    fun initFoodOutletBasicDetails(item: FoodOutletItem) {
        foodOutletBasicDetails.value = item
    }

    fun initFoodOutletExtraDetails(item: FoodOutletDetailsDataModel) {
        foodOutletExtraDetails.value = item
    }
}