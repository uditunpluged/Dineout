package com.phantasmist.dineout.Cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.phantasmist.dineout.Cache.dao.CachedFoodOutletsDao
import com.phantasmist.dineout.Cache.model.CachedFoodOutletItem

@Database(entities = arrayOf(CachedFoodOutletItem::class), version = 1)
abstract class FoodOutletDatabase : RoomDatabase() {
    abstract fun cachedFoodOutletDao(): CachedFoodOutletsDao

    companion object {
        private var INSTANCE: FoodOutletDatabase? = null
        //helps to access database ins asynchronized fashion
        private val lock = Any()

        fun getInstance(context: Context): FoodOutletDatabase {
            if (INSTANCE == null) {
                //using syncronised helps the function to protect from multiple threads
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, FoodOutletDatabase::class.java, "foodoutlets.db").build()
                    }
                    return INSTANCE as FoodOutletDatabase
                }
            }
            return INSTANCE as FoodOutletDatabase
        }

    }
}