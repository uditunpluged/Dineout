package com.phantasmist.dineout.DependencyInjection.modules

import android.app.Application
import com.phantasmist.dineout.Cache.db.FoodOutletDatabase
import dagger.Module
import dagger.Provides
/**
 * This module provides application wide injectable instance of database
 * */
@Module
abstract class CacheModule{

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDataBase(application: Application): FoodOutletDatabase {
            return FoodOutletDatabase.getInstance(application)
        }
    }
}