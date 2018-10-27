package com.phantasmist.dineout.AppModules.Home.mappers

import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.Home.model.ExploreApiResponse
import com.phantasmist.dineout.Base.ModelMapper
import javax.inject.Inject

/**
 * Mapper class to map data from Explore api response to our usable data model
 * */
class ExploreApiResponseMapper @Inject constructor() : ModelMapper<ExploreApiResponse.Response.Group.Item, FoodOutletItem> {
    override fun mapFromModel(model: ExploreApiResponse.Response.Group.Item): FoodOutletItem {
        return FoodOutletItem(null,model.venue?.id!!,
                model.venue?.name!!,
                model.venue?.location?.formattedAddress.toString(),
                model.venue?.location?.lat,
                model.venue?.location?.lng,
                model.venue?.categories?.first()?.name.orEmpty(),
                model.venue?.categories?.first()?.icon?.prefix.plus("64").plus(model.venue?.categories?.first()?.icon?.suffix))
    }
}