package com.phantasmist.dineout.AppModules.home.mappers

import com.phantasmist.dineout.AppModules.home.datamodel.FoodOutletItem
import com.phantasmist.dineout.AppModules.home.model.ExploreApiResponse
import com.phantasmist.dineout.base.ModelMapper
import javax.inject.Inject

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