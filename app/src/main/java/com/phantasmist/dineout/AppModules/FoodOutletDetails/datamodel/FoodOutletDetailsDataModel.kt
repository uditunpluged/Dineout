package com.phantasmist.dineout.AppModules.FoodOutletDetails.datamodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class FoodOutletDetailsDataModel @Inject constructor(val id: String,
                                                          val name: String,
                                                          val phone: String?,
                                                          val rating: String?,
                                                          val openingHrsStatus: String,
                                                          val imageUrl: String = "") : Parcelable