package com.reda.modulotechtest

import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType
import com.reda.modulotechtest.repository.DeviceRepository
import com.reda.modulotechtest.ui.DevicesViewState
import com.reda.modulotechtest.ui.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class HomeViewModelText {

    private lateinit var viewModel: HomeViewModel
    private val mockkDeviceRepository: DeviceRepository = mockk(relaxed = true)

    @Before
    fun setup(){
        viewModel = HomeViewModel(mockkDeviceRepository)
    }

    @Test
    fun `Filter test `() = runTest {

        // Given
        val list = listOf(
            Device(
                id = 1,
                deviceName = "name",
                deviceType = DeviceType.RollerShutter(position = 50)
            ),
            Device(
                id = 2,
                deviceName = "light",
                deviceType = DeviceType.Light(intensity = 12, isOn = false)
            ),
            Device(
                id = 2,
                deviceName = "heater",
                deviceType = DeviceType.Heater(temperature = 12.2f, isOn = true)
            )
        )
        coEvery { viewModel.devices.first() } returns list

        // When
        viewModel.onHeaterClicked()

        // Then
        val expected = listOf(
            Device(
            id = 2,
            deviceName = "heater",
            deviceType = DeviceType.Heater(temperature = 12.2f, isOn = true)
            )
        )
        val stateAfter = viewModel.viewState.value
        Assert.assertTrue(stateAfter is DevicesViewState.Devices)
        Assert.assertEquals((stateAfter as DevicesViewState.Devices).devices,expected)
    }

    @Test
    fun `Delete Device`() = runTest {
        // Given
        val device1 = Device(
            id = 1,
            deviceName = "name",
            deviceType = DeviceType.RollerShutter(position = 50)
        )
        val device2 = Device(
            id = 2,
            deviceName = "light",
            deviceType = DeviceType.Light(intensity = 12, isOn = false)
        )
        val device3 = Device(
            id = 2,
            deviceName = "heater",
            deviceType = DeviceType.Heater(temperature = 12.2f, isOn = true)
        )
        coEvery { viewModel.devices.first() } returns listOf(device1,device2,device3)

        // When
        viewModel.onDeleteDeviceClicked(device2)

        // Then
        val expected = listOf(device1,device3)
        val stateAfter = viewModel.devices.value
        Assert.assertEquals(stateAfter,expected)
    }
}