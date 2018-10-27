package com.phantasmist.dineout.Cache.mapper

import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.Cache.model.CachedFoodOutletItem
import javax.inject.Inject

/**
 * MAPPER CLASS TO MAP DATA TO/FROM CACHE
 * */
class CachedFoodOutletMapper @Inject constructor() : CacheMapper<CachedFoodOutletItem, FoodOutletItem> {
    override fun mapFromCached(type: CachedFoodOutletItem): FoodOutletItem {
        return FoodOutletItem(type.id, type.outletId,
                type.name, type.address, type.lat, type.lng, type.category, type.iconUrl, type.disliked)
    }

    override fun mapToCache(type: FoodOutletItem): CachedFoodOutletItem {
        return CachedFoodOutletItem(0, type.outletId,
                type.name, type.address, type.lat, type.lng, type.category, type.iconUrl, type.disliked)
    }

}
