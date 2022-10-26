package com.reda.modulotechtest

import com.reda.modulotechtest.network.DeviceDataSource
import com.reda.modulotechtest.persistence.dao.HeaterDao
import com.reda.modulotechtest.persistence.dao.LightDao
import com.reda.modulotechtest.persistence.dao.RollerShutterDao
import com.reda.modulotechtest.persistence.model.LightEntity
import com.reda.modulotechtest.repository.DeviceRepository
import com.reda.modulotechtest.repository.DeviceRepositoryImpl
import io.mockk.MockKSettings.relaxed
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DeviceRepositoryTest {

    private lateinit var deviceRepository: DeviceRepository
    private val mockkLightDao: LightDao = mockk(relaxed=true)
    private val mockkRollerShutterDao: RollerShutterDao = mockk(relaxed=true)
    private val mockkHeaterDAo: HeaterDao = mockk(relaxed=true)
    private val deviceDataSource: DeviceDataSource = mockk(relaxed=true)

    @Before
    fun setup(){
        deviceRepository = DeviceRepositoryImpl(
            mockkLightDao,
            mockkRollerShutterDao,
            mockkHeaterDAo,
            deviceDataSource
        )
    }

    @Test
    fun `Update Light`() = runTest {
        // When
        deviceRepository.updateLight(
            id = 1,
            name = "name",
            intensity = 50,
            mode = true
        )

        // Then
        coVerify {
            mockkLightDao.updateLight(
                LightEntity(
                    id = 1,
                    deviceName = "name",
                    intensity = 50,
                    isOn = true
                )
            )
        }
    }
}