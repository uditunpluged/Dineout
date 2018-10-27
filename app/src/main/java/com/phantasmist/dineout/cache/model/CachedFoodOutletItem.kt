package com.phantasmist.dineout.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.phantasmist.dineout.cache.db.ProjectConstants
import javax.inject.Inject


@Entity(tableName = ProjectConstants.TABLE_NAME)
data class CachedFoodOutletItem @Inject constructor(@PrimaryKey(autoGenerate = true)
                                                    var id: Long,
                                                    @ColumnInfo(name = ProjectConstants.COLUMN_PROJECT_ID)
                                                    var outletId: String,
                                                    var name: String,
                                                    var address: String,
                                                    var lat: Double?,
                                                    var lng: Double?,
                                                    var category: String,
                                                    @ColumnInfo(name = "icon_url")
                                                    var iconUrl: String,
                                                    @ColumnInfo(name = ProjectConstants.COLUMN_IS_DISLIKED)
                                                    var disliked: Boolean = false) {


    @Ignore
    constructor() : this(0,"","","",0.0,0.0,"","",false)
}