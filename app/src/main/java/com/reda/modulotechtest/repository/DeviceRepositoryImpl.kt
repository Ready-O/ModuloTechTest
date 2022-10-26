package com.reda.modulotechtest.repository

import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType
import com.reda.modulotechtest.network.DeviceDataSource
import com.reda.modulotechtest.persistence.dao.HeaterDao
import com.reda.modulotechtest.persistence.dao.LightDao
import com.reda.modulotechtest.persistence.dao.RollerShutterDao
import com.reda.modulotechtest.persistence.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeviceRepositoryImpl @Inject constructor(
    private val lightDao: LightDao,
    private val rollerShutterDao: RollerShutterDao,
    private val heaterDao: HeaterDao,
    private val deviceDataSource: DeviceDataSource
) : DeviceRepository {

    override val devicesFlow: Flow<Result<List<Device>>> = combine(
        lightDao.getAll(),
        rollerShutterDao.getAll(),
        heaterDao.getAll()
    ){ lights, rollers, heaters ->
        val devices = mutableListOf<Device>()
        lights.forEach { devices.add(it.toDevice()) }
        rollers.forEach { devices.add(it.toDevice()) }
        heaters.forEach { devices.add(it.toDevice()) }
        if (devices.isNotEmpty()){
            Result.success(devices.sortDevices())
        } else {
            refresh()
        }
    }.flowOn(Dispatchers.IO)

    private fun List<Device>.sortDevices(): List<Device> {
        val sortedDevices = mutableListOf<Device>()
        val size = this.size
        for (index in 1..size){
            sortedDevices.add(this.find { it.id == index }!!)
        }
        return sortedDevices
    }

    override suspend fun refreshDevices(){
        withContext(Dispatchers.IO){
            refresh()
        }
    }

    private suspend fun refresh(): Result<List<Device>>{
        return deviceDataSource.fetchDevices().onSuccess {
            addDevicesToDatabase(it)
        }
    }

    private suspend fun addDevicesToDatabase(devices: List<Device>) {
        devices.forEach { device ->
            when(device.deviceType){
                is DeviceType.Light -> lightDao.insertItem(device.toLightEntity())
                is DeviceType.RollerShutter -> rollerShutterDao.insertItem(device.toRollerShutterEntity())
                is DeviceType.Heater -> heaterDao.insertItem(device.toHeaterEntity())
            }
        }
    }

    override fun getDevice(id: Int): Flow<Result<Device>> = devicesFlow.map{ result ->
        if (result.isSuccess){
            val device = result.getOrThrow().find { it.id == id }
            if (device == null){
                Result.failure(Exception())
            }
            else{
                Result.success(device)
            }
        }
        else{
            Result.failure(Exception())
        }
    }

    override suspend fun updateLight(id: Int, name: String, mode: Boolean, intensity: Int) {
        lightDao.updateItem(
            LightEntity(
                id = id,
                deviceName = name,
                intensity = intensity,
                isOn = mode
            )
        )
    }

    override suspend fun updateRollerShutter(id: Int, name: String, position: Int) {
        rollerShutterDao.updateItem(
            RollerShutterEntity(
                id = id,
                deviceName = name,
                position = position
            )
        )
    }
}