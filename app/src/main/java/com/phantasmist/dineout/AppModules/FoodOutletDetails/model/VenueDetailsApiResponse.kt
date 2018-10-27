package com.phantasmist.dineout.AppModules.FoodOutletDetails.model
import com.google.gson.annotations.SerializedName



data class VenueDetailsApiResponse(
    @SerializedName("response") val response: Response? = Response(),
    @SerializedName("meta") val meta: Meta? = Meta()
) {

    data class Response(
        @SerializedName("venue") val venue: Venue? = Venue()
    ) {

        data class Venue(
            @SerializedName("id") val id: String? = "",
            @SerializedName("name") val name: String? = "",
            @SerializedName("contact") val contact: Contact? = Contact(),
            @SerializedName("location") val location: Location? = Location(),
            @SerializedName("canonicalUrl") val canonicalUrl: String? = "",
            @SerializedName("verified") val verified: Boolean? = false,
            @SerializedName("url") val url: String? = "",
            @SerializedName("price") val price: Price? = Price(),
            @SerializedName("dislike") val dislike: Boolean? = false,
            @SerializedName("ok") val ok: Boolean? = false,
            @SerializedName("rating") val rating: Double? = 0.0,
            @SerializedName("ratingColor") val ratingColor: String? = "",
            @SerializedName("ratingSignals") val ratingSignals: Int? = 0,
            @SerializedName("allowMenuUrlEdit") val allowMenuUrlEdit: Boolean? = false,
            @SerializedName("photos") val photos: Photos? = Photos(),
            @SerializedName("reasons") val reasons: Reasons? = Reasons(),
            @SerializedName("createdAt") val createdAt: Int? = 0,
            @SerializedName("shortUrl") val shortUrl: String? = "",
            @SerializedName("timeZone") val timeZone: String? = "",
            @SerializedName("hours") val hours: Hours? = Hours(),
            @SerializedName("bestPhoto") val bestPhoto: BestPhoto? = BestPhoto()
        ) {

            data class Reasons(
                @SerializedName("count") val count: Int? = 0,
                @SerializedName("items") val items: List<Item?>? = listOf()
            ) {

                data class Item(
                    @SerializedName("summary") val summary: String? = "",
                    @SerializedName("type") val type: String? = "",
                    @SerializedName("reasonName") val reasonName: String? = ""
                )
            }


            data class Contact(
                @SerializedName("phone") val phone: String? = "",
                @SerializedName("formattedPhone") val formattedPhone: String? = ""
            )


            data class Location(
                @SerializedName("address") val address: String? = "",
                @SerializedName("lat") val lat: Double? = 0.0,
                @SerializedName("lng") val lng: Double? = 0.0,
                @SerializedName("labeledLatLngs") val labeledLatLngs: List<LabeledLatLng?>? = listOf(),
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


            data class Price(
                @SerializedName("tier") val tier: Int? = 0,
                @SerializedName("message") val message: String? = "",
                @SerializedName("currency") val currency: String? = ""
            )


            data class Photos(
                @SerializedName("count") val count: Int? = 0,
                @SerializedName("groups") val groups: List<Group?>? = listOf(),
                @SerializedName("summary") val summary: String? = ""
            ) {

                data class Group(
                    @SerializedName("type") val type: String? = "",
                    @SerializedName("name") val name: String? = "",
                    @SerializedName("count") val count: Int? = 0,
                    @SerializedName("items") val items: List<Item?>? = listOf()
                ) {

                    data class Item(
                        @SerializedName("id") val id: String? = "",
                        @SerializedName("createdAt") val createdAt: Int? = 0,
                        @SerializedName("source") val source: Source? = Source(),
                        @SerializedName("prefix") val prefix: String? = "",
                        @SerializedName("suffix") val suffix: String? = "",
                        @SerializedName("width") val width: Int? = 0,
                        @SerializedName("height") val height: Int? = 0,
                        @SerializedName("user") val user: User? = User(),
                        @SerializedName("visibility") val visibility: String? = ""
                    ) {

                        data class User(
                            @SerializedName("id") val id: String? = "",
                            @SerializedName("firstName") val firstName: String? = "",
                            @SerializedName("lastName") val lastName: String? = "",
                            @SerializedName("gender") val gender: String? = "",
                            @SerializedName("photo") val photo: Photo? = Photo()
                        ) {

                            data class Photo(
                                @SerializedName("prefix") val prefix: String? = "",
                                @SerializedName("suffix") val suffix: String? = ""
                            )
                        }


                        data class Source(
                            @SerializedName("name") val name: String? = "",
                            @SerializedName("url") val url: String? = ""
                        )
                    }
                }
            }


            data class BestPhoto(
                @SerializedName("id") val id: String? = "",
                @SerializedName("createdAt") val createdAt: Int? = 0,
                @SerializedName("prefix") val prefix: String? = "",
                @SerializedName("suffix") val suffix: String? = "",
                @SerializedName("width") val width: Int? = 0,
                @SerializedName("height") val height: Int? = 0,
                @SerializedName("visibility") val visibility: String? = "",
                @SerializedName("source") val source: Source? = Source()
            ) {

                data class Source(
                    @SerializedName("name") val name: String? = "",
                    @SerializedName("url") val url: String? = ""
                )
            }


            data class Hours(
                @SerializedName("status") val status: String? = "",
                @SerializedName("richStatus") val richStatus: RichStatus? = RichStatus(),
                @SerializedName("isOpen") val isOpen: Boolean? = false,
                @SerializedName("isLocalHoliday") val isLocalHoliday: Boolean? = false,
                @SerializedName("dayData") val dayData: List<Any?>? = listOf(),
                @SerializedName("timeframes") val timeframes: List<Timeframe?>? = listOf()
            ) {

                data class RichStatus(
                    @SerializedName("entities") val entities: List<Any?>? = listOf(),
                    @SerializedName("text") val text: String? = ""
                )


                data class Timeframe(
                    @SerializedName("days") val days: String? = "",
                    @SerializedName("includesToday") val includesToday: Boolean? = false,
                    @SerializedName("open") val open: List<Open?>? = listOf(),
                    @SerializedName("segments") val segments: List<Any?>? = listOf()
                ) {

                    data class Open(
                        @SerializedName("renderedTime") val renderedTime: String? = ""
                    )
                }
            }
        }
    }


    data class Meta(
        @SerializedName("code") val code: Int? = 0,
        @SerializedName("requestId") val requestId: String? = ""
    )
}