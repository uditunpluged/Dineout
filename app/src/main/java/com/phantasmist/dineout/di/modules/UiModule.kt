package com.phantasmist.dineout.di.modules

import com.phantasmist.dineout.AppModules.FoodOutletDetails.view.FoodOutletDetailsActivity
import com.phantasmist.dineout.AppModules.home.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeFoodOutletDetailsActivity(): FoodOutletDetailsActivity


}