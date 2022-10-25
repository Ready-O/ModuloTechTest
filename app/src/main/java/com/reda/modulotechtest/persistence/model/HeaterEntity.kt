package com.reda.modulotechtest.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType

@Entity(
    tableName = "heaters"
)
data class HeaterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "device_name")
    val deviceName: String,
    val temperature: Float,
    @ColumnInfo(name = "is_on")
    val isOn: Boolean
)

fun HeaterEntity.toDevice(): Device =
    Device(
        id = this.id,
        deviceName = this.deviceName,
        deviceType = DeviceType.Heater(
            temperature = this.temperature,
            isOn = this.isOn
        )
    )

fun Device.toHeaterEntity(): HeaterEntity =
    HeaterEntity(
        id = this.id,
        deviceName = this.deviceName,
        temperature = (this.deviceType as DeviceType.Heater).temperature,
        isOn = (this.deviceType as DeviceType.Heater).isOn
    )