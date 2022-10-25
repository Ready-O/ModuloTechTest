package com.reda.modulotechtest.network

import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.toDevice
import javax.inject.Inject

class DeviceDataSourceImpl @Inject constructor(
    private val deviceApi: DeviceApi
): DeviceDataSource {

    override suspend fun fetchDevices(): Result<List<Device>> {
        try {
            val networkResponse = deviceApi.fetchData()
            val devices = mutableListOf<Device>()
            networkResponse.devices.forEach{
                it.toDevice().onSuccess { device ->
                    devices.add(device)
                }
                    .onFailure {
                        return Result.failure(Exception())
                    }
            }
            return Result.success(devices)
        }
        catch (e: Exception){
            return Result.failure(e)
        }
    }
}