package com.reda.modulotechtest.ui.devicesteering

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.reda.modulotechtest.R
import com.reda.modulotechtest.databinding.FragmentDeviceSteeringBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeviceSteeringFragment : Fragment() {

    private val navigationArgs: DeviceSteeringFragmentArgs by navArgs()
    private val viewModel: DeviceSteeringViewModel by viewModels()
    private var _binding: FragmentDeviceSteeringBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceSteeringBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.generateDeviceToControl(navigationArgs.deviceId)

        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.viewState.collect {
                when(it){
                    DeviceSteeringViewState.Loading -> {
                        binding.loadingView.visibility = VISIBLE
                        binding.lightView.visibility = GONE
                        binding.rollerView.visibility = GONE
                        binding.heaterView.visibility = GONE
                        binding.errorView.visibility = GONE
                    }
                    is DeviceSteeringViewState.Error -> {
                        binding.loadingView.visibility = GONE
                        binding.lightView.visibility = GONE
                        binding.rollerView.visibility = GONE
                        binding.heaterView.visibility = GONE
                        binding.errorView.visibility = VISIBLE
                    }
                    is DeviceSteeringViewState.Light -> {
                        binding.loadingView.visibility = GONE
                        binding.lightView.visibility = VISIBLE
                        binding.rollerView.visibility = GONE
                        binding.heaterView.visibility = GONE
                        binding.errorView.visibility = GONE

                        binding.lightName.text = it.name

                        binding.lightMode.isChecked = it.mode

                        binding.intensityText.text = getString(
                            R.string.intensity,it.intensity
                        )
                        binding.intensitySlider.value = it.intensity.toFloat()
                        binding.intensitySlider.addOnChangeListener { slider, value, fromUser ->
                            binding.intensityText.text = getString(
                                R.string.intensity,value.toInt()
                            )
                        }

                        binding.lightCta.setOnClickListener {
                            viewModel.onLightCtaClicked(
                                mode = binding.lightMode.isChecked,
                                intensity = binding.intensitySlider.value.toInt()
                            )
                            findNavController().navigateUp()
                        }
                    }
                    is DeviceSteeringViewState.RollerShutter -> {
                        binding.loadingView.visibility = GONE
                        binding.lightView.visibility = GONE
                        binding.rollerView.visibility = VISIBLE
                        binding.heaterView.visibility = GONE
                        binding.errorView.visibility = GONE

                        binding.rollerName.text = it.name

                        binding.positionText.text = getString(R.string.position,it.position)
                        binding.positionSlider.value = it.position.toFloat()
                        binding.positionSlider.addOnChangeListener{_, value, _ ->
                            binding.positionText.text = getString(R.string.position,value.toInt())
                        }

                        binding.rollerCta.setOnClickListener {
                            viewModel.onRollerCtaClicked(binding.positionSlider.value.toInt())
                            findNavController().navigateUp()
                        }
                    }
                    is DeviceSteeringViewState.Heater -> {
                        binding.loadingView.visibility = GONE
                        binding.lightView.visibility = GONE
                        binding.rollerView.visibility = GONE
                        binding.heaterView.visibility = VISIBLE
                        binding.errorView.visibility = GONE

                        binding.heaterName.text = it.name

                        binding.heaterMode.isChecked = it.mode

                        binding.temperatureText.text = getString(R.string.temperature,it.temperature)
                        binding.temperatureSlider.value = it.temperature
                        binding.temperatureSlider.addOnChangeListener { _, value, _ ->
                            binding.temperatureText.text = getString(R.string.temperature,value)
                        }

                        binding.heaterCta.setOnClickListener {
                            viewModel.onHeaterCtaClicked(
                                mode = binding.heaterMode.isChecked,
                                temperature = binding.temperatureSlider.value
                            )
                            findNavController().navigateUp()
                        }
                    }
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}