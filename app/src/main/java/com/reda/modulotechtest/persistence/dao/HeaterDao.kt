package com.reda.modulotechtest.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reda.modulotechtest.persistence.model.HeaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeaterDao {

    @Query("SELECT * FROM heaters")
    fun getAll(): Flow<List<HeaterEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(heater: HeaterEntity)
}