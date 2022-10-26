package com.reda.modulotechtest.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.reda.modulotechtest.persistence.model.LightEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LightDao {

    @Query("SELECT * FROM lights")
    fun getAll(): Flow<List<LightEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(light: LightEntity)

    @Update
    suspend fun updateLight(light: LightEntity)

}