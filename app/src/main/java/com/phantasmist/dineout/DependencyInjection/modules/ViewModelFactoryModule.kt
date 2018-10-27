package com.phantasmist.dineout.DependencyInjection.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.phantasmist.dineout.AppModules.FoodOutletDetails.viewmodel.FoodOutletDetailViewModel
import com.phantasmist.dineout.AppModules.Home.viewmodel.HomeActivityViewModel
import com.phantasmist.dineout.Base.ViewModelFactory
import com.phantasmist.dineout.Base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {
    /**
     * Provides ViewModelFactory
     * */

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    /**
     * Provides HomeActivityViewModel
     * */
    @Binds
    @IntoMap
    @ViewModelKey(HomeActivityViewModel::class)
    abstract fun bindMyViewModel(myViewModel: HomeActivityViewModel): ViewModel


    /**
     * Provides FoodOutletDetailViewModel
     * */
    @Binds
    @IntoMap
    @ViewModelKey(FoodOutletDetailViewModel::class)
    abstract fun bindFODViewModel(myViewModel: FoodOutletDetailViewModel): ViewModel
}