package com.phantasmist.dineout.Base

import io.reactivex.disposables.Disposable
/**
 * BASE CONTRACT TO BE USED BY EACH APP MODULE
 * */
class BaseContract {

    interface BasePresenter<in T> {
        fun subscribe(subscribedDisposable: Disposable)
        fun unsubscribe()
        fun attach(view:T)
    }

    interface View{

    }
}