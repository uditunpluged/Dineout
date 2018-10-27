package com.phantasmist.dineout.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.phantasmist.dineout.AppModules.FoodOutletDetails.mapper.VenueDetailsApiResponseMapper
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActPresenterImpl
import com.phantasmist.dineout.AppModules.home.mappers.ExploreApiResponseMapper
import com.phantasmist.dineout.AppModules.home.presenter.MainActiPresenterImpl
import com.phantasmist.dineout.BuildConfig
import com.phantasmist.dineout.api.FourSquareApiInterface
import com.phantasmist.dineout.api.FourSquareService
import com.phantasmist.dineout.cache.FoodOutletCacheImpl
import com.phantasmist.dineout.cache.db.FoodOutletDatabase
import com.phantasmist.dineout.cache.mapper.CachedFoodOutletMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideForSquareService(): FourSquareApiInterface {
        return FourSquareService.makeFourSquareService(BuildConfig.DEBUG)
    }


    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }


    @Provides
    fun provideMainPresenter(apiServive: FourSquareApiInterface, mapper: ExploreApiResponseMapper): MainActiPresenterImpl {
        return MainActiPresenterImpl(apiServive, mapper)
    }

    @Provides
    fun provideFoodOutletDetailsActivityPresenter(apiServive: FourSquareApiInterface, mapper: VenueDetailsApiResponseMapper): FoodOutletDetailActPresenterImpl {
        return FoodOutletDetailActPresenterImpl(apiServive, mapper)
    }

    @Provides
    fun provideFoodOutletCacheImpl(database:FoodOutletDatabase,mapper: CachedFoodOutletMapper):FoodOutletCacheImpl{
        return FoodOutletCacheImpl(database,mapper)
    }

}