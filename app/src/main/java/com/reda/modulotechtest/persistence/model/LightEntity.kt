package com.reda.modulotechtest.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType

@Entity(
    tableName = "lights"
)
data class LightEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "device_name")
    val deviceName: String,
    val intensity: Int,
    @ColumnInfo(name = "is_on")
    val isOn: Boolean
)

fun LightEntity.toDevice() : Device =
    Device(
        id = this.id,
        deviceName = this.deviceName,
        deviceType = DeviceType.Light(
            intensity = this.intensity,
            isOn = this.isOn
        )
    )

fun Device.toLightEntity() : LightEntity =
    LightEntity(
        id = this.id,
        deviceName = this.deviceName,
        intensity = (this.deviceType as DeviceType.Light).intensity,
        isOn = (this.deviceType as DeviceType.Light).isOn
    )

