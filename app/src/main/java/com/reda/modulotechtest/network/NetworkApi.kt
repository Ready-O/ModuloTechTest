package com.reda.modulotechtest.network

import retrofit2.http.GET

interface NetworkApi {
    @GET("modulotest/data.json")
    suspend fun fetchData(): ApiResponse
}