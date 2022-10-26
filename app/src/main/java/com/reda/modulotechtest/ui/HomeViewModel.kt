package com.reda.modulotechtest.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reda.modulotechtest.model.Device
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
class HomeViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository
): ViewModel() {

    private val _devices = MutableStateFlow<List<Device>> (listOf())
    val devices: StateFlow<List<Device>> = _devices.asStateFlow()

    private val _viewState = MutableStateFlow<DevicesViewState>(DevicesViewState.Loading)
    val viewState: StateFlow<DevicesViewState> = _viewState.asStateFlow()

    init{
        viewModelScope.launch {
            deviceRepository.devicesFlow.collectLatest { result ->
                result.onSuccess {
                    _devices.value = it
                    _viewState.value = DevicesViewState.Devices(devices.value)
                }
                    .onFailure {
                        _viewState.value = DevicesViewState.Error(it)
                    }
            }
        }
    }

    fun onAllClicked(){
        viewModelScope.launch {
            _viewState.value = DevicesViewState.Devices(devices.value)
        }
    }

    fun onLightClicked(){
        viewModelScope.launch {
            val list = devices.value.filter { it.deviceType is DeviceType.Light }
            _viewState.value = DevicesViewState.Devices(list)
        }
    }

    fun onRollerClicked(){
        viewModelScope.launch {
            val list = devices.value.filter { it.deviceType is DeviceType.RollerShutter }
            _viewState.value = DevicesViewState.Devices(list)
        }
    }

    fun onHeaterClicked(){
        viewModelScope.launch {
            val list = devices.value.filter { it.deviceType is DeviceType.Heater }
            _viewState.value = DevicesViewState.Devices(list)
        }
    }
}