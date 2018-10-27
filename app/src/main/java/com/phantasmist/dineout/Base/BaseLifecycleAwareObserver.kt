package com.phantasmist.dineout.Base

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActPresenterImpl
import com.phantasmist.dineout.AppModules.Home.presenter.HomeActiPresenterImpl
import javax.inject.Inject

/**
 *LIFECYCLE OBSERVER FOR HANDLING LIFECYCLE OF ACTIVITY IN A BETTER ORGANIZED WAY
 * */

class BaseLifecycleAwareObserver<P> @Inject constructor(val presenter: P) : LifecycleObserver {


    private val LOG_TAG = "BASE_LIFECYCLE_AWARE_OBSERVER"

    @SuppressLint("LongLogTag")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun listeningToCreate() {
        Log.d(LOG_TAG, "onCreate()")
    }

    @SuppressLint("LongLogTag")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun listeningToDestroy() {
        Log.d(LOG_TAG, "onDestroy()")
        Log.d(LOG_TAG, "UNSUBSCRIBING TO THREADS")
        when {
            presenter is HomeActiPresenterImpl -> (presenter as HomeActiPresenterImpl).unsubscribe()
            presenter is FoodOutletDetailActPresenterImpl -> (presenter as FoodOutletDetailActPresenterImpl).unsubscribe()
        }
    }
}