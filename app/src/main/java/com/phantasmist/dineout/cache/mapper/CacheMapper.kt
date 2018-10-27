package com.phantasmist.dineout.cache.mapper

interface CacheMapper<C,E> {
    fun mapFromCached(type:C):E

    fun mapToCache(type: E):C
}