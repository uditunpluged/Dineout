package com.phantasmist.dineout.Base

/**
 * COMMON INTERFACE TO MAP DATA TO/FROM TWO DIFFERENT MODELS
 * */
interface ModelMapper<in M,out E> {

    fun mapFromModel(model:M):E
}