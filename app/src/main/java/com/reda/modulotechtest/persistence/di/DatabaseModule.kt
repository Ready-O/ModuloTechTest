package com.reda.modulotechtest.persistence.di

import android.content.Context
import androidx.room.Room
import com.reda.modulotechtest.persistence.AppDatabase
import com.reda.modulotechtest.persistence.dao.HeaterDao
import com.reda.modulotechtest.persistence.dao.LightDao
import com.reda.modulotechtest.persistence.dao.RollerShutterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app-database"
    ).build()

    @Provides
    fun providesLightDao(
        database: AppDatabase
    ): LightDao = database.lightDao()

    @Provides
    fun providesRollerShutterDao(
        database: AppDatabase
    ): RollerShutterDao = database.rollerShutterDao()

    @Provides
    fun providesHeaterDao(
        database: AppDatabase
    ): HeaterDao = database.heaterDao()
}