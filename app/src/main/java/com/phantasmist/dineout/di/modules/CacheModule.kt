package com.phantasmist.dineout.di.modules

import android.app.Application
import com.phantasmist.dineout.cache.db.FoodOutletDatabase
import dagger.Module
import dagger.Provides

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