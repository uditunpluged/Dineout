package com.phantasmist.dineout.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.phantasmist.dineout.cache.db.ProjectConstants.DELETE_PROJECTS
import com.phantasmist.dineout.cache.db.ProjectConstants.QUERY_DISLIKED_PROJECTS
import com.phantasmist.dineout.cache.db.ProjectConstants.QUERY_PROJECTS
import com.phantasmist.dineout.cache.db.ProjectConstants.QUERY_UPDATE_DISLIKED_STATUS
import com.phantasmist.dineout.cache.model.CachedFoodOutletItem
import io.reactivex.Flowable

@Dao
abstract class CachedFoodOutletsDao {
    @Query(QUERY_PROJECTS)
    @JvmSuppressWildcards
    abstract fun getFoodOutlets(): Flowable<List<CachedFoodOutletItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertFoodOutlets(projects: List<CachedFoodOutletItem>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_DISLIKED_PROJECTS)
    abstract fun getDislikeedProjects(): Flowable<List<CachedFoodOutletItem>>

    @Query(QUERY_UPDATE_DISLIKED_STATUS)
    abstract fun setDislikeStatus(is_disliked: Boolean,
                                  outlet_id: String)

}