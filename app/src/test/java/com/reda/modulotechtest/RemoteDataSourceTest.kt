package com.reda.modulotechtest

import com.reda.modulotechtest.model.*
import com.reda.modulotechtest.network.ApiResponse
import com.reda.modulotechtest.network.NetworkApi
import com.reda.modulotechtest.network.RemoteDataSource
import com.reda.modulotechtest.network.RemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

import org.junit.Before


class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private val mockkNetworkApi: NetworkApi = mockk(relaxed = true)

    @Before
    fun setup(){
        remoteDataSource = RemoteDataSourceImpl(mockkNetworkApi)
    }

    @Test
    fun `Fetch devices successful`() = runTest{

        // Given
        val mockkUser = User("","",
            Address("","","","",""),
        ""
        )
        val networkDevice = NetworkDevice(id = 1, deviceName = "name",
        productType = "RollerShutter", intensity = null, mode = null, position = 50,
        temperature = null)
        val apiResponse = ApiResponse(devices = listOf(networkDevice),
        user = mockkUser)
        coEvery { mockkNetworkApi.fetchData() } returns apiResponse

        // Expected
        val mockkRoller = Device(
            id = 1,
            deviceName = "name",
            deviceType = DeviceType.RollerShutter(position = 50)
        )

        // When
        val result = remoteDataSource.fetchDevices()

        coVerify {
            mockkNetworkApi.fetchData()
        }
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(result, Result.success(listOf(mockkRoller)))

    }

    @Test
    fun `Fetch User successful`() = runTest{

        // Given
        val mockkUser = User("","",
            Address("","","","",""),
            ""
        )
        val networkDevice = NetworkDevice(id = 1, deviceName = "name",
            productType = "RollerShutter", intensity = null, mode = null, position = 50,
            temperature = null)
        val apiResponse = ApiResponse(devices = listOf(networkDevice),
            user = mockkUser)
        coEvery { mockkNetworkApi.fetchData() } returns apiResponse

        // When
        val result = remoteDataSource.fetchUser()

        coVerify {
            mockkNetworkApi.fetchData()
        }
        Assert.assertTrue(result.isSuccess)
        Assert.assertEquals(result, Result.success(mockkUser))
    }
}