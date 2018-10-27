package com.phantasmist.dineout.Cache.repository

import com.phantasmist.dineout.AppModules.Home.datamodel.FoodOutletItem
import io.reactivex.Observable

interface FoodOutletDataStore {

    fun saveFoodOutlets(foodOutlets:List<FoodOutletItem>)
    fun getFoodOutlets(): Observable<List<FoodOutletItem>>
    fun dislikeFoodOutlet(foodOutletId:String,dislikeStatus:Boolean)
    fun getDislikedProjects(): Observable<List<FoodOutletItem>>
}