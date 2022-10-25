package com.reda.modulotechtest.persistence.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType
import com.reda.modulotechtest.persistence.dao.RollerShutterDao

@Entity(
    tableName = "roller_shutters"
)
data class RollerShutterEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "device_name")
    val deviceName: String,
    val position: Int
)


fun RollerShutterEntity.toDevice(): Device =
    Device(
        id = this.id,
        deviceName = this.deviceName,
        deviceType = DeviceType.RollerShutter(
            position = this.position
        )
    )

fun Device.toRollerShutterEntity(): RollerShutterEntity =
    RollerShutterEntity(
        id = this.id,
        deviceName = this.deviceName,
        position = (this.deviceType as DeviceType.RollerShutter).position
    )