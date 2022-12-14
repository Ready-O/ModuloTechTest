package com.reda.modulotechtest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.reda.modulotechtest.persistence.dao.HeaterDao
import com.reda.modulotechtest.persistence.dao.LightDao
import com.reda.modulotechtest.persistence.dao.RollerShutterDao
import com.reda.modulotechtest.persistence.dao.UserDao
import com.reda.modulotechtest.persistence.model.HeaterEntity
import com.reda.modulotechtest.persistence.model.LightEntity
import com.reda.modulotechtest.persistence.model.RollerShutterEntity
import com.reda.modulotechtest.persistence.model.UserEntity

@Database(
    entities = [LightEntity::class, RollerShutterEntity::class, HeaterEntity::class, UserEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun lightDao(): LightDao
    abstract fun rollerShutterDao(): RollerShutterDao
    abstract fun heaterDao(): HeaterDao
    abstract fun userDao(): UserDao
}