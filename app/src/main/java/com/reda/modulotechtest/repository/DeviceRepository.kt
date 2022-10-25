package com.reda.modulotechtest.repository

import com.reda.modulotechtest.model.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface DeviceRepository {

    val devicesFlow: Flow<Result<List<Device>>>
    suspend fun refreshDevices()
}