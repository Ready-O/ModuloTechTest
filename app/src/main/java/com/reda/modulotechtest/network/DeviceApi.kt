package com.reda.modulotechtest.network

import retrofit2.http.GET

interface DeviceApi {
    @GET("modulotest/data.json")
    suspend fun fetchData(): ApiResponse
}