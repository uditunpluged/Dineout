package com.phantasmist.dineout.AppModules.Home.presenter

import android.util.Log
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.Home.mappers.ExploreApiResponseMapper
import com.phantasmist.dineout.Cache.FoodOutletCacheImpl
import com.phantasmist.dineout.Remote.FourSquareApiInterface
import com.phantasmist.dineout.Utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * This class is the P (Presenter) of MVP architecture
 *
 * */
class HomeActiPresenterImpl @Inject constructor(val apiService: FourSquareApiInterface, val mapper: ExploreApiResponseMapper) : HomeActivityContract.MAPresenter {


    protected val subscriptions: CompositeDisposable = CompositeDisposable()
    lateinit var view: HomeActivityContract.MAView
    lateinit var cacheImpl: FoodOutletCacheImpl

    /**
     * Checks wether the data exists in local db or not
     * if yes --> loads data from db(cache)
     * if no --> loads data from remote api
     * */
    override fun loadDataFromApi() {
        view.showProgress()

        var cacheSubscription = cacheImpl.getFoodOutlets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("isDatabase", it.isEmpty().toString())
                    if (it.isEmpty()) {
                        getDataFromRemote()
                    } else {
                        view.onDataLoaded(it, true)
                        view.hideProgress()
                    }
                }, { error ->
                    view.hideProgress()
                    view.onErrorFromService(error.localizedMessage)
                }, {
                    view.hideProgress()
                    unsubscribe()
                })
        subscribe(cacheSubscription)
    }


    /**
     * Loads data from api and then store it in database
     * */
    private fun getDataFromRemote() {
        var subscribe = apiService.getNearbyOutlets("40.719981, -74.0022634", 50, "Food", Constants.CLIENT_ID, Constants.CLIENT_SECRET, "201810101", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.meta?.code == 200) {
                        var foodOutletList = it.response?.groups?.first()?.items?.map { mapper.mapFromModel(it!!) }?.asSequence()?.toList()!!.toMutableList()
                        saveDataToCache(foodOutletList)
                        view.onDataLoaded(foodOutletList, false)
                    } else {
                        view.onErrorFromService(it.meta?.errorDetail!!.toString())
                    }
                }, { error ->
                    view.hideProgress()
                    view.onErrorFromService(error.localizedMessage)
                }, {
                    view.hideProgress()
                })
        subscribe(subscribe)

    }

    /**
     * Adds disposable to list of composite disposables
     * */
    override fun subscribe(subscribedDisposable: Disposable) {
        subscriptions.add(subscribedDisposable)
    }

    /**
     * Clears all subscriptions
     * */
    override fun unsubscribe() {
        Log.e("UNSUBSCRIBING", "" + subscriptions.size())
        subscriptions.clear()
        Log.e("UNSUBSCRIBED", "" + subscriptions.size())
    }

    override fun attach(view: HomeActivityContract.MAView) {
        this.view = view
    }

    override fun attachCacheImplObject(cacheImpl: FoodOutletCacheImpl) {
        this.cacheImpl = cacheImpl
    }

    /**
     * Saves data to cache(db)
     * */
    override fun saveDataToCache(items: List<FoodOutletItem>) {
        cacheImpl.saveFoodOutlets(items)
    }


}