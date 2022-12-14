package com.reda.modulotechtest.repository

import com.reda.modulotechtest.model.Device
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {

    val devicesFlow: Flow<Result<List<Device>>>
    suspend fun refreshDevices()
    fun getDevice(id: Int): Flow<Result<Device>>
    suspend fun updateLight(
        id: Int,
        name: String,
        mode: Boolean,
        intensity: Int
    )
    suspend fun updateRollerShutter(
        id: Int,
        name: String,
        position: Int
    )
    suspend fun updateHeater(
        id: Int,
        name: String,
        mode: Boolean,
        temperature: Float
    )
}