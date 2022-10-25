package com.reda.modulotechtest.network

import com.reda.modulotechtest.model.NetworkDevice
import com.reda.modulotechtest.model.User

data class ApiResponse(
    val devices: List<NetworkDevice>,
    val user: User
)
