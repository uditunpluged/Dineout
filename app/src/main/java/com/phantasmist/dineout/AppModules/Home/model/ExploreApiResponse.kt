package com.phantasmist.dineout.AppModules.Home.model

import com.google.gson.annotations.SerializedName

/**
 * Model class to map Foursquare Api Response
 * */
data class ExploreApiResponse(
        @SerializedName("meta") val meta: Meta? = Meta(),
        @SerializedName("response") val response: Response? = Response()
) {

    data class Response(
            @SerializedName("suggestedFilters") val suggestedFilters: SuggestedFilters? = SuggestedFilters(),
            @SerializedName("warning") val warning: Warning? = Warning(),
            @SerializedName("suggestedRadius") val suggestedRadius: Int? = 0,
            @SerializedName("headerLocation") val headerLocation: String? = "",
            @SerializedName("headerFullLocation") val headerFullLocation: String? = "",
            @SerializedName("headerLocationGranularity") val headerLocationGranularity: String? = "",
            @SerializedName("totalResults") val totalResults: Int? = 0,
            @SerializedName("groups") val groups: List<Group?>? = listOf()
    ) {

        data class Warning(
                @SerializedName("text") val text: String? = ""
        )


        data class Group(
                @SerializedName("type") val type: String? = "",
                @SerializedName("name") val name: String? = "",
                @SerializedName("items") val items: List<Item?>? = listOf()
        ) {

            data class Item(
                    @SerializedName("venue") val venue: Venue? = Venue(),
                    @SerializedName("referralId") val referralId: String? = ""
            ) {


                data class Venue(
                        @SerializedName("id") val id: String? = "",
                        @SerializedName("name") val name: String? = "",
                        @SerializedName("location") val location: Location? = Location(),
                        @SerializedName("categories") val categories: List<Category?>? = listOf(),
                        @SerializedName("photos") val photos: Photos? = Photos()
                ) {

                    data class Category(
                            @SerializedName("id") val id: String? = "",
                            @SerializedName("name") val name: String? = "",
                            @SerializedName("pluralName") val pluralName: String? = "",
                            @SerializedName("shortName") val shortName: String? = "",
                            @SerializedName("icon") val icon: Icon? = Icon(),
                            @SerializedName("primary") val primary: Boolean? = false
                    ) {

                        data class Icon(
                                @SerializedName("prefix") val prefix: String? = "",
                                @SerializedName("suffix") val suffix: String? = ""
                        )
                    }


                    data class Location(
                            @SerializedName("address") val address: String? = "",
                            @SerializedName("lat") val lat: Double? = 0.0,
                            @SerializedName("lng") val lng: Double? = 0.0,
                            @SerializedName("labeledLatLngs") val labeledLatLngs: List<LabeledLatLng?>? = listOf(),
                            @SerializedName("distance") val distance: Int? = 0,
                            @SerializedName("cc") val cc: String? = "",
                            @SerializedName("city") val city: String? = "",
                            @SerializedName("state") val state: String? = "",
                            @SerializedName("country") val country: String? = "",
                            @SerializedName("formattedAddress") val formattedAddress: List<String?>? = listOf()
                    ) {

                        data class LabeledLatLng(
                                @SerializedName("label") val label: String? = "",
                                @SerializedName("lat") val lat: Double? = 0.0,
                                @SerializedName("lng") val lng: Double? = 0.0
                        )
                    }


                    data class Photos(
                            @SerializedName("count") val count: Int? = 0,
                            @SerializedName("groups") val groups: List<Any?>? = listOf()
                    )
                }
            }
        }


        data class SuggestedFilters(
                @SerializedName("header") val header: String? = "",
                @SerializedName("filters") val filters: List<Filter?>? = listOf()
        ) {

            data class Filter(
                    @SerializedName("name") val name: String? = "",
                    @SerializedName("key") val key: String? = ""
            )
        }


    }


    data class Meta(
            @SerializedName("code") val code: Int? = 0,
            @SerializedName("requestId") val requestId: String? = "",
            @SerializedName("errorDetail") val errorDetail: String? = ""
    )
}