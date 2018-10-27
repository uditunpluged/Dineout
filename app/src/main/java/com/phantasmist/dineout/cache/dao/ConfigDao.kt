package com.phantasmist.dineout.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.phantasmist.dineout.cache.db.ConfigConstants
import com.phantasmist.dineout.cache.model.Config
import io.reactivex.Flowable


@Dao
abstract class ConfigDao{
    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig(): Flowable<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)
}