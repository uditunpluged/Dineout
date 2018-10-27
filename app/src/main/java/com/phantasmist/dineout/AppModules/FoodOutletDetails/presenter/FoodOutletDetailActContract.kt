package com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter

import com.phantasmist.dineout.AppModules.FoodOutletDetails.datamodel.FoodOutletDetailsDataModel
import com.phantasmist.dineout.base.BaseContract
import com.phantasmist.dineout.cache.FoodOutletCacheImpl

class FoodOutletDetailActContract {
    interface FODAView : BaseContract.View {
        fun onDataLoadedFromService(mapFromModel: FoodOutletDetailsDataModel)
        fun onErrorFromService(localizedMessage: String)
        fun showProgress()
        fun hideProgress()
    }

    interface FODAPresenter : BaseContract.BasePresenter<FODAView> {
        fun loadDataFromVenueDetailsApi(id: String)
        fun dislikeFoodOutlet(outletId: String, dislikeStatus: Boolean)
        fun attachCacheImplObject(cacheImpl: FoodOutletCacheImpl)

    }
}