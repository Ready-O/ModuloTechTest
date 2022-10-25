package com.reda.modulotechtest.network

import com.reda.modulotechtest.model.Device

interface DeviceDataSource {

    suspend fun fetchDevices(): Result<List<Device>>
}