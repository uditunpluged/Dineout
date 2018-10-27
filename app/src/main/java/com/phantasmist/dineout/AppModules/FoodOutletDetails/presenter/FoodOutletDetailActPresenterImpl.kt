package com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter

import android.util.Log
import com.phantasmist.dineout.AppModules.FoodOutletDetails.mapper.VenueDetailsApiResponseMapper
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
 * */
class FoodOutletDetailActPresenterImpl @Inject constructor(val apiService: FourSquareApiInterface, val mapper: VenueDetailsApiResponseMapper) : FoodOutletDetailActContract.FODAPresenter {

    protected val subscriptions: CompositeDisposable = CompositeDisposable()
    lateinit var view: FoodOutletDetailActContract.FODAView
    lateinit var cacheImpl: FoodOutletCacheImpl

    override fun loadDataFromVenueDetailsApi(id: String) {
        view.showProgress()
        var subscribe = apiService.getVenueDetails(id, Constants.CLIENT_ID, Constants.CLIENT_SECRET, "20181010")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.meta?.code == 200) {
                        view.onDataLoadedFromService(mapper.mapFromModel(it.response?.venue!!))
                    } else {
                        view.onErrorFromService(it.meta?.errorDetail!!.toString())
                    }

                }, { error ->
                    view.hideProgress()
                    view.onErrorFromService(error.localizedMessage)
                }, {
                    view.hideProgress()
                    unsubscribe()
                })

        subscribe(subscribe)
    }

    override fun dislikeFoodOutlet(outletId: String, dislikeStatus: Boolean) {
        cacheImpl.dislikeFoodOutlet(outletId, dislikeStatus)
    }

    override fun subscribe(subscribedDisposable: Disposable) {
        subscriptions.add(subscribedDisposable)
    }

    override fun unsubscribe() {
        Log.e("UNSUBSCRIBING", "" + subscriptions.size())
        subscriptions.clear()
        Log.e("UNSUBSCRIBED", "" + subscriptions.size())
    }

    override fun attach(view: FoodOutletDetailActContract.FODAView) {
        this.view = view
    }

    override fun attachCacheImplObject(cacheImpl: FoodOutletCacheImpl) {
        this.cacheImpl = cacheImpl
    }

}