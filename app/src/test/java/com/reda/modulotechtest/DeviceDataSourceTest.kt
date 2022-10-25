package com.reda.modulotechtest

import com.reda.modulotechtest.model.*
import com.reda.modulotechtest.network.ApiResponse
import com.reda.modulotechtest.network.DeviceApi
import com.reda.modulotechtest.network.DeviceDataSource
import com.reda.modulotechtest.network.DeviceDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before


class DeviceDataSourceTest {

    private lateinit var deviceDataSource: DeviceDataSource
    private val mockkDeviceApi: DeviceApi = mockk(relaxed = true)

    @Before
    fun setup(){
        deviceDataSource = DeviceDataSourceImpl(mockkDeviceApi)
    }

    @Test
    fun `Fetch devices successful`() = runTest{

        // Given
        val mockkUser = User("","",
            Address("","","","",""),
        ""
        )
        val networkDevice = NetworkDevice(id = "id", deviceName = "name",
        productType = "RollerShutter", intensity = null, mode = null, position = 50,
        temperature = null)
        val apiResponse = ApiResponse(devices = listOf(networkDevice),
        user = mockkUser)
        coEvery { mockkDeviceApi.fetchData() } returns apiResponse

        // Expected
        val mockkRoller = Device(
            id = "id",
            deviceName = "name",
            deviceType = DeviceType.RollerShutter(position = 50)
        )

        // When
        val result = deviceDataSource.fetchDevices()

        coVerify {
            mockkDeviceApi.fetchData()
        }
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(result, Result.success(listOf(mockkRoller)))

    }
}