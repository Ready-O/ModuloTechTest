package com.reda.modulotechtest.ui.homepage

import com.reda.modulotechtest.model.Device

interface DevicesViewState {

    object Loading: DevicesViewState

    data class Devices(val devices: List<Device>): DevicesViewState

    data class Error(val error: Throwable): DevicesViewState
}