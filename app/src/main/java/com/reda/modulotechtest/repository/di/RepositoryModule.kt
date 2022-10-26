package com.reda.modulotechtest.repository.di

import com.reda.modulotechtest.repository.DeviceRepository
import com.reda.modulotechtest.repository.DeviceRepositoryImpl
import com.reda.modulotechtest.repository.UserRepository
import com.reda.modulotechtest.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsDeviceRepository(
        deviceRepository: DeviceRepositoryImpl
    ): DeviceRepository

    @Binds
    @Singleton
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}