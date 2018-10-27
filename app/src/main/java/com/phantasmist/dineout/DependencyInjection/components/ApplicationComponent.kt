package com.phantasmist.dineout.DependencyInjection.components

import android.app.Application
import com.phantasmist.dineout.DineoutApplication
import com.phantasmist.dineout.DependencyInjection.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        ApplicationModule::class,
        CacheModule::class,
        UiModule::class,
        UtilsModule::class,
        ViewModelFactoryModule::class))
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        /**
         * @BindInstance annotation  allows us to declare our component build is bound to this
         * application component type
         * Meaning we can use our application instance to retreive the builder for this function
         */
        @BindsInstance
        fun application(application: Application): Builder

        /**
         * sthis function provides us back the instance of application component
         * */
        fun build(): ApplicationComponent
    }

    //this will take our application instance and tell dagger to pop back the dependencies which we may define in this class
    fun inject(app: DineoutApplication)
}