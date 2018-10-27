package com.phantasmist.dineout.DependencyInjection.modules

import com.phantasmist.dineout.AppModules.FoodOutletDetails.view.FoodOutletDetailsActivity
import com.phantasmist.dineout.AppModules.Home.view.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Module provides activites
 * */
@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeFoodOutletDetailsActivity(): FoodOutletDetailsActivity


}