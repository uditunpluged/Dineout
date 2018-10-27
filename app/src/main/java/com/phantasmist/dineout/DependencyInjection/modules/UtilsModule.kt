package com.phantasmist.dineout.DependencyInjection.modules

import com.phantasmist.dineout.AppModules.FoodOutletDetails.mapper.VenueDetailsApiResponseMapper
import com.phantasmist.dineout.AppModules.FoodOutletDetails.presenter.FoodOutletDetailActPresenterImpl
import com.phantasmist.dineout.AppModules.Home.mappers.ExploreApiResponseMapper
import com.phantasmist.dineout.AppModules.Home.presenter.HomeActiPresenterImpl
import com.phantasmist.dineout.BuildConfig
import com.phantasmist.dineout.Cache.FoodOutletCacheImpl
import com.phantasmist.dineout.Cache.db.FoodOutletDatabase
import com.phantasmist.dineout.Cache.mapper.CachedFoodOutletMapper
import com.phantasmist.dineout.Remote.FourSquareApiInterface
import com.phantasmist.dineout.Remote.FourSquareService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    /**
     * Provides instance of FourSquareApiInterface
     * */
    @Provides
    @Singleton
    fun provideForSquareService(): FourSquareApiInterface {
        return FourSquareService.makeFourSquareService(BuildConfig.DEBUG)
    }


    /**
     * Provides instance of HomePresenterImpl
     * */
    @Provides
    fun provideHomePresenter(apiServive: FourSquareApiInterface, mapper: ExploreApiResponseMapper): HomeActiPresenterImpl {
        return HomeActiPresenterImpl(apiServive, mapper)
    }

    /**
     * Provides instance of FoodOutletDetailActPresenterImpl
     * */
    @Provides
    fun provideFoodOutletDetailsActivityPresenter(apiServive: FourSquareApiInterface, mapper: VenueDetailsApiResponseMapper): FoodOutletDetailActPresenterImpl {
        return FoodOutletDetailActPresenterImpl(apiServive, mapper)
    }


    /**
     * Provides instance of FoodOutletCacheImpl
     * */
    @Provides
    fun provideFoodOutletCacheImpl(database:FoodOutletDatabase,mapper: CachedFoodOutletMapper):FoodOutletCacheImpl{
        return FoodOutletCacheImpl(database,mapper)
    }

}