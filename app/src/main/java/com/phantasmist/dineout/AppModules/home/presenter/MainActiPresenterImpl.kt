package com.phantasmist.dineout.AppModules.home.presenter

import android.util.Log
import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.home.mappers.ExploreApiResponseMapper
import com.phantasmist.dineout.Utils.Constants
import com.phantasmist.dineout.api.FourSquareApiInterface
import com.phantasmist.dineout.cache.FoodOutletCacheImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainActiPresenterImpl @Inject constructor(val apiService: FourSquareApiInterface, val mapper: ExploreApiResponseMapper) : MainActivityContract.MAPresenter {


    protected val subscriptions: CompositeDisposable = CompositeDisposable()
    lateinit var view: MainActivityContract.MAView
    lateinit var cacheImpl: FoodOutletCacheImpl

    override fun loadDataFromApi() {
        view.showProgress()

        var cacheSubscription = cacheImpl.getFoodOutlets().subscribeOn(Schedulers.io())
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


    private fun getDataFromRemote() {
        var subscribe = apiService.getNearbyOutlets("40.719981, -74.0022634", 50, "Food", Constants.CLIENT_ID, Constants.CLIENT_SECRET, "201810101", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    var foodOutletList = it.response?.groups?.first()?.items?.map { mapper.mapFromModel(it!!) }?.asSequence()?.toList()!!
                    saveDataToCache(foodOutletList)
                    view.onDataLoaded(foodOutletList, false)
                }, { error ->
                    view.hideProgress()
                    view.onErrorFromService(error.localizedMessage)
                }, {
                    view.hideProgress()
                    unsubscribe()
                    getDat()
                })
        subscribe(subscribe)

    }

    override fun subscribe(subscribedDisposable: Disposable) {
        subscriptions.add(subscribedDisposable)
    }

    override fun unsubscribe() {
        Log.e("UNSUBSCRIBING", "" + subscriptions.size())
        subscriptions.clear()
        Log.e("UNSUBSCRIBED", "" + subscriptions.size())
    }

    override fun attach(view: MainActivityContract.MAView) {
        this.view = view
    }

    override fun attachCacheImplObject(cacheImpl: FoodOutletCacheImpl) {
        this.cacheImpl = cacheImpl
    }

    override fun saveDataToCache(items: List<FoodOutletItem>) {
        cacheImpl.saveFoodOutlets(items)
    }

    fun getDat() {
        cacheImpl.getFoodOutlets().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            Log.e("LIST SIZE", it.size.toString())
        })
    }


}