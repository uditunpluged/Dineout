package com.phantasmist.dineout.base

interface ModelMapper<in M,out E> {

    fun mapFromModel(model:M):E
}