package com.phantasmist.dineout.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.phantasmist.dineout.AppModules.FoodOutletDetails.viewmodel.FoodOutletDetailViewModel
import com.phantasmist.dineout.AppModules.home.viewmodel.MainActivityViewModel
import com.phantasmist.dineout.base.ViewModelFactory
import com.phantasmist.dineout.base.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMyViewModel(myViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FoodOutletDetailViewModel::class)
    abstract fun bindFODViewModel(myViewModel: FoodOutletDetailViewModel): ViewModel
}