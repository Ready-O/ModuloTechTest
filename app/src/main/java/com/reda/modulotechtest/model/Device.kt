package com.reda.modulotechtest.model

data class Device(
    val id: String,
    val deviceName: String,
    val deviceType: DeviceType
)

interface DeviceType{
    data class Light(
        val intensity: Int,
        val isOn: Boolean
    ): DeviceType
    data class RollerShutter(
        val position: Int
    ) : DeviceType
    data class Heater(
        val temperature: Int,
        val isOn: Boolean
    ) : DeviceType
}
