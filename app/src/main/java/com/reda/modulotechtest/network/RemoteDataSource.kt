package com.reda.modulotechtest.network

import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.User

interface RemoteDataSource {

    suspend fun fetchDevices(): Result<List<Device>>
    suspend fun fetchUser(): Result<User>
}