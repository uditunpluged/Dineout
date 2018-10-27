package com.phantasmist.dineout.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Thids module provides application wide injectable dependecies
 * */
@Module
abstract class ApplicationModule {
    /**
     * we use binds annotation to return the instance odf injected parameter
     *
     * Now whenever the context instance is required we will use our injected application instance instance
     * to return an instance odf context of required class
     *
     * Newt we need to add this to a list of modules in our ApplicationComponent class so that our dagger configuration
     * is aware of this module
     * */


    @Binds
    abstract fun bindContext(application: Application): Context

}