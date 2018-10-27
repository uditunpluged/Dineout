package com.phantasmist.dineout.Base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * THIS IS A FACTORY CLASS WHICH PROVIDES THE INSTANCE OF VIEWMODEL
 *
 * DAGGER WILL CREATE A MAP OF OBJECTS WITH A SPECIFIC KEY AND HENCE WILL RETURN SAME
 * INSTANCE OF REQUIRED VIEWMODEL
 *
 * WE WILL CREATE A DAGGER MODULE --> ViewModelFactoryModule TO PROVIDE ViewModelFactory
 *
 * */
open class ViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalStateException("Unknown model class: " + modelClass)
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}