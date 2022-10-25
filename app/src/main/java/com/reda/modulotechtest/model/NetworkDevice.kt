package com.reda.modulotechtest.model

data class NetworkDevice(
    val id: String,
    val deviceName: String,
    val productType: String,
    val intensity: Int?,
    val mode: String?,
    val position: Int?,
    val temperature: Int?,
)

fun NetworkDevice.toDevice(): Result<Device>{
    return Result.success(
        Device(
            id = this.id,
            deviceName = this.deviceName,
            deviceType = when(productType){
                "Light" -> DeviceType.Light(
                    intensity = this.intensity ?: return Result.failure(Exception()),
                    isOn = reformatMode(this.mode)
                )
                "RollerShutter" -> DeviceType.RollerShutter(
                    position = this.position ?: return Result.failure(Exception())
                )
                "Heater" -> DeviceType.Heater(
                    temperature = this.temperature ?: return Result.failure(Exception()),
                    isOn = reformatMode(this.mode)
                )
                else -> return Result.failure(Exception())
            }
        )
    )
}

fun reformatMode(mode: String?): Boolean{
    return when(mode){
        "ON" -> true
        "OFF" -> false
        else -> false
    }
}
