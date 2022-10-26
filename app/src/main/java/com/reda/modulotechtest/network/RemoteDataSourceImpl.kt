package com.reda.modulotechtest.network

import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.User
import com.reda.modulotechtest.model.toDevice
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val networkApi: NetworkApi
): RemoteDataSource {

    override suspend fun fetchDevices(): Result<List<Device>> {
        try {
            val networkResponse = networkApi.fetchData()
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

    override suspend fun fetchUser(): Result<User> {
        return try{
            val networkResponse = networkApi.fetchData()
            Result.success(networkResponse.user)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}