package com.reda.modulotechtest.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reda.modulotechtest.R
import com.reda.modulotechtest.databinding.DeviceItemBinding
import com.reda.modulotechtest.model.Device
import com.reda.modulotechtest.model.DeviceType

class DevicesAdapter(private val onDeviceClick: (Device) -> Unit):
    ListAdapter<Device,DevicesAdapter.DeviceViewHolder>(DiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        return DeviceViewHolder(
            DeviceItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onDeviceClick(current)
        }

        val res = holder.itemView.context.resources
        var deviceName = ""
        var deviceDetail = ""
        when(current.deviceType){
            is DeviceType.Light -> {
                deviceName = "${current.deviceName} (${if (current.deviceType.isOn) "On" else "Off"})"
                deviceDetail = res.getString(R.string.intensity,current.deviceType.intensity)
            }
            is DeviceType.RollerShutter -> {
                deviceName = current.deviceName
                deviceDetail = res.getString(R.string.position,current.deviceType.position)
            }
            is DeviceType.Heater -> {
                deviceName = "${current.deviceName} (${if (current.deviceType.isOn) "On" else "Off"})"
                deviceDetail = res.getString(R.string.temperature,current.deviceType.temperature)
            }
        }

        holder.bind(
            item = current,
            deviceName = deviceName,
            deviceDetail = deviceDetail
        )
    }

    class DeviceViewHolder(private var binding: DeviceItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Device, deviceName: String, deviceDetail: String) {
            binding.deviceName.text = deviceName
            binding.deviceDetail.text = deviceDetail
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Device>() {
            override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}