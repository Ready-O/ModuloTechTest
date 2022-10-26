package com.reda.modulotechtest.ui.devicesteering

interface DeviceSteeringViewState {

    object Loading: DeviceSteeringViewState

    data class Light(
        val id: Int,
        val name: String,
        val intensity: Int,
        val mode: Boolean
    ): DeviceSteeringViewState

    data class RollerShutter(
        val id: Int,
        val name: String,
        val position: Int
    ): DeviceSteeringViewState

    data class Heater(
        val id: Int,
        val name: String,
        val temperature: Float,
        val mode: Boolean
    ): DeviceSteeringViewState

    data class Error(val throwable: Throwable): DeviceSteeringViewState
}