package com.phantasmist.dineout.api

import com.phantasmist.dineout.AppModules.FoodOutletDetails.model.VenueDetailsApiResponse
import com.phantasmist.dineout.AppModules.home.model.ExploreApiResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface FourSquareApiInterface {
    @GET("venues/explore")
    fun getNearbyOutlets(@Query("ll") latLan: String,
                         @Query("limit") limit: Int,
                         @Query("section") type: String,
                         @Query("client_id") clientId: String,
                         @Query("client_secret") clientSecret: String,
                         @Query("v") date: String,
                         @Query("sortByDistance") sortDistance: Int): Observable<ExploreApiResponse>

    @GET("venues/{venue_id}")
    fun getVenueDetails(@Path("venue_id") venueId: String,
                        @Query("client_id") clientId: String,
                        @Query("client_secret") clientSecret: String,
                        @Query("v") date: String): Observable<VenueDetailsApiResponse>
}