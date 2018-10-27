package com.phantasmist.dineout.AppModules.home.presenter

import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.base.BaseContract
import com.phantasmist.dineout.cache.FoodOutletCacheImpl

class MainActivityContract {
    interface MAView :BaseContract.View{
        fun onDataLoaded(items: List<FoodOutletItem>,isFromCache:Boolean)
        fun onErrorFromService(localizedMessage: String)
        fun showProgress()
        fun hideProgress()
    }

    interface MAPresenter:BaseContract.BasePresenter<MAView>{
        fun loadDataFromApi()
        fun saveDataToCache(items: List<FoodOutletItem>)
        fun attachCacheImplObject(cacheImpl: FoodOutletCacheImpl)
    }
}