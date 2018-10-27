package com.phantasmist.dineout.Cache

import android.util.Log
import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.Cache.db.FoodOutletDatabase
import com.phantasmist.dineout.Cache.mapper.CachedFoodOutletMapper
import com.phantasmist.dineout.Cache.repository.FoodOutletDataStore
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * THIS CLASS IS RESPONSIBLE FOR PERFORMING ROOM DATABASE OPERATIONS
 *
 * this class implements method from FoodOutletDataStore
 * */
class FoodOutletCacheImpl @Inject constructor(private val foodOutletDatabase: FoodOutletDatabase,
                                              private val foodOutletMapper: CachedFoodOutletMapper) : FoodOutletDataStore {

    protected val subscriptions: CompositeDisposable = CompositeDisposable()

    /**
     * METHOD TO ADD DISPOSABLE OBJECT TO LIST OF SUBSCRIPTIONS
     * */
    fun subscribe(subscribedDisposable: Disposable) {
        subscriptions.add(subscribedDisposable)
    }

    /**
     * METHOD TO REMOVE ALL DISPOSABLE
     * */
    fun unsubscribe() {
        Log.e("UNSUBS_DB", "" + subscriptions.size())
        subscriptions.clear()
        Log.e("UNSUBS_DB", "" + subscriptions.size())
    }

    /**
     * SAVES FOOD OUTLETS TO DB
     * */
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


    /**
     * GETS FOOD OUTLETS FROM DB
     * */
    override fun getFoodOutlets(): Observable<List<FoodOutletItem>> {
        return foodOutletDatabase.cachedFoodOutletDao().getFoodOutlets().toObservable().map {
            it.map {
                foodOutletMapper.mapFromCached(it)
            }
        }
    }


    /**
     * MARKS STATUS OF FOODOUTLET AS DISLIKED OR UNDISLIKED
     * */

    override fun dislikeFoodOutlet(foodOutletId: String, dislikeStatus: Boolean) {
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

    /**
     * GETS UNDISLIKED PROJECTS
     * */
    override fun getDislikedProjects(): Observable<List<FoodOutletItem>> {
        return foodOutletDatabase.cachedFoodOutletDao().getDislikeedProjects().toObservable().map {
            it.map {
                foodOutletMapper.mapFromCached(it)
            }
        }
    }

}