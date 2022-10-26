package com.reda.modulotechtest.ui.devicesteering

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reda.modulotechtest.model.DeviceType
import com.reda.modulotechtest.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceSteeringViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
): ViewModel(){

    private val _viewState = MutableStateFlow<DeviceSteeringViewState>(DeviceSteeringViewState.Loading)
    val viewState: StateFlow<DeviceSteeringViewState> = _viewState.asStateFlow()

    fun generateDeviceToControl(id: Int){
        viewModelScope.launch{
            deviceRepository.getDevice(id).collectLatest { result ->
                result.onSuccess { device ->
                    when(device.deviceType){
                        is DeviceType.Light -> {
                            _viewState.value = DeviceSteeringViewState.Light(
                                id = device.id,
                                name = device.deviceName,
                                intensity = device.deviceType.intensity,
                                mode = device.deviceType.isOn
                            )
                        }
                        is DeviceType.RollerShutter -> {
                            _viewState.value = DeviceSteeringViewState.RollerShutter(
                                id = device.id,
                                name = device.deviceName,
                                position = device.deviceType.position
                            )
                        }
                        is DeviceType.Heater -> {
                            _viewState.value = DeviceSteeringViewState.Heater(
                                id = device.id,
                                name = device.deviceName,
                                temperature = device.deviceType.temperature,
                                mode = device.deviceType.isOn
                            )
                        }
                    }
                }
                    .onFailure {
                        _viewState.value = DeviceSteeringViewState.Error(it)
                    }
            }
        }
    }

    fun onLightCtaClicked(mode: Boolean, intensity: Int){
        viewModelScope.launch{
            val light = (viewState.value as DeviceSteeringViewState.Light)
            deviceRepository.updateLight(
                id = light.id,
                name = light.name,
                mode = mode,
                intensity = intensity
            )
        }
    }
}