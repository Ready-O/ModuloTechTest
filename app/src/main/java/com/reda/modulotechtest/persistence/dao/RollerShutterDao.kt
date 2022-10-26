package com.reda.modulotechtest.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.reda.modulotechtest.persistence.model.RollerShutterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RollerShutterDao {

    @Query("SELECT * FROM roller_shutters")
    fun getAll(): Flow<List<RollerShutterEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(rollerShutter: RollerShutterEntity)

    @Update
    suspend fun updateItem(rollerShutter: RollerShutterEntity)
}