package com.phantasmist.dineout.Base

import android.arch.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass
/**
 * TO FURTHER BIND VIEWMODEL AND CREATE MAP , we need multibindings and to do it we use specific annotation
 * @ViewModelKey which represents the key of our map
 * */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)