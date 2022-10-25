package com.reda.modulotechtest.network.di

import com.reda.modulotechtest.network.DeviceApi
import com.reda.modulotechtest.network.DeviceDataSource
import com.reda.modulotechtest.network.DeviceDataSourceImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindsDeviceDataSource(
        deviceDataSource: DeviceDataSourceImpl
    ): DeviceDataSource

    companion object {
        @Provides
        @Singleton
        fun providesMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        @Singleton
        fun providesDeviceApi(): DeviceApi {
            return Retrofit.Builder()
                .baseUrl("http://storage42.com/")
                .addConverterFactory(MoshiConverterFactory.create(providesMoshi()))
                .build()
                .create(DeviceApi::class.java)
        }
    }
}