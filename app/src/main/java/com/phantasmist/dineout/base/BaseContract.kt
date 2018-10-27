package com.phantasmist.dineout.base

import io.reactivex.disposables.Disposable

class BaseContract {

    interface BasePresenter<in T> {
        fun subscribe(subscribedDisposable: Disposable)
        fun unsubscribe()
        fun attach(view:T)
    }

    interface View{

    }
}