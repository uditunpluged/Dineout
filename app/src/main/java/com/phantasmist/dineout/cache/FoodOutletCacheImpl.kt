package com.phantasmist.dineout.cache

import android.util.Log
import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.cache.db.FoodOutletDatabase
import com.phantasmist.dineout.cache.mapper.CachedFoodOutletMapper
import com.phantasmist.dineout.cache.repository.FoodOutletDataStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FoodOutletCacheImpl @Inject constructor(private val foodOutletDatabase: FoodOutletDatabase,
                                              private val foodOutletMapper: CachedFoodOutletMapper) : FoodOutletDataStore {

    protected val subscriptions: CompositeDisposable = CompositeDisposable()

    fun subscribe(subscribedDisposable: Disposable) {
        subscriptions.add(subscribedDisposable)
    }

    fun unsubscribe() {
        Log.e("UNSUBS_DB", "" + subscriptions.size())
        subscriptions.clear()
        Log.e("UNSUBS_DB", "" + subscriptions.size())
    }

    override fun saveFoodOutlets(foodOutlets: List<FoodOutletItem>) {
        var subs = Observable.fromCallable {
            foodOutletDatabase.cachedFoodOutletDao().insertFoodOutlets(foodOutlets.map {
                foodOutletMapper.mapToCache(it)
            })
        }.subscribeOn(Schedulers.io()).subscribe({

        }, { error ->
            Log.e("SAVED TO ERROR", "" + error.localizedMessage)
        }, {
            Log.e("SAVED TO BB", "Complete")
            unsubscribe()
        })
        subscribe(subs)
    }


    override fun getFoodOutlets(): Observable<List<FoodOutletItem>> {
        return foodOutletDatabase.cachedFoodOutletDao().getFoodOutlets().toObservable().map {
            it.map {
                foodOutletMapper.mapFromCached(it)
            }
        }
    }



    override fun dislikeFoodOutlet(foodOutletId: String,dislikeStatus:Boolean)  {
        var subs = Observable.fromCallable {
            foodOutletDatabase.cachedFoodOutletDao().setDislikeStatus(dislikeStatus, foodOutletId)
        }.subscribeOn(Schedulers.io()).subscribe({
        }, { error ->
            Log.e("SAVED TO ERROR", "" + error.localizedMessage)
        }, {
            Log.e("SAVED TO BB", "Complete")
            unsubscribe()
        })
        subscribe(subs)
    }

    override fun getDislikedProjects(): Observable<List<FoodOutletItem>> {
        return foodOutletDatabase.cachedFoodOutletDao().getDislikeedProjects().toObservable().map {
            it.map {
                foodOutletMapper.mapFromCached(it)
            }
        }
    }

}