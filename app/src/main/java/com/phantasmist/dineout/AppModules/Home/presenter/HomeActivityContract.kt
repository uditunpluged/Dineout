package com.phantasmist.dineout.AppModules.Home.presenter

import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.Base.BaseContract
import com.phantasmist.dineout.Cache.FoodOutletCacheImpl

/**
 * Contract between HomeActivity(View) and HomeActiPresenterImpl(presenter)
 * */
class HomeActivityContract {
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