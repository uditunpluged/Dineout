package com.phantasmist.dineout.base

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActPresenterImpl
import com.phantasmist.dineout.AppModules.home.presenter.MainActiPresenterImpl
import javax.inject.Inject

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
            presenter is MainActiPresenterImpl -> (presenter as MainActiPresenterImpl).unsubscribe()
            presenter is FoodOutletDetailActPresenterImpl -> (presenter as FoodOutletDetailActPresenterImpl).unsubscribe()
        }
    }
}