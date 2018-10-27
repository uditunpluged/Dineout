package com.phantasmist.dineout.AppModules.FoodOutletDetails.mapper

import com.phantasmist.dineout.AppModules.FoodOutletDetails.datamodel.FoodOutletDetailsDataModel
import com.phantasmist.dineout.AppModules.FoodOutletDetails.model.VenueDetailsApiResponse
import com.phantasmist.dineout.base.ModelMapper
import javax.inject.Inject

class VenueDetailsApiResponseMapper @Inject constructor():ModelMapper<VenueDetailsApiResponse.Response.Venue,FoodOutletDetailsDataModel>{


    override fun mapFromModel(model: VenueDetailsApiResponse.Response.Venue): FoodOutletDetailsDataModel {
        return FoodOutletDetailsDataModel(model.id!!,
                model.name!!,
                model.contact?.phone,
                model.rating.toString(),
                model.hours?.status!!,
                model.bestPhoto?.prefix.plus(model.bestPhoto?.width).plus("x").plus(model.bestPhoto?.height).plus(model.bestPhoto?.suffix))
    }
}