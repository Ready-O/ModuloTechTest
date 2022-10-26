package com.reda.modulotechtest.ui.homepage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.reda.modulotechtest.R
import com.reda.modulotechtest.databinding.FragmentHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomePageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DevicesAdapter(
            onDeleteClick = viewModel::onDeleteDeviceClicked,
            onDeviceClick = {
                val action =
                    HomePageFragmentDirections.actionHomePageFragmentToDeviceSteeringFragment(it)
                this.findNavController().navigate(action)
            }
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.viewState.collect {
                when(it){
                    DevicesViewState.Loading -> {
                        binding.loadingView.visibility = VISIBLE
                        binding.devicesView.visibility = GONE
                        binding.errorView.visibility = GONE
                    }
                    is DevicesViewState.Error -> {
                        binding.loadingView.visibility = GONE
                        binding.devicesView.visibility = GONE
                        binding.errorView.visibility = VISIBLE
                        binding.errorView.text = it.error.message
                    }
                    is DevicesViewState.Devices -> {
                        binding.loadingView.visibility = GONE
                        binding.devicesView.visibility = VISIBLE
                        binding.errorView.visibility = GONE
                        adapter.submitList(it.devices)
                        binding.chipAll.setOnClickListener {
                            viewModel.onAllClicked()
                        }
                        binding.chipLight.setOnClickListener {
                            viewModel.onLightClicked()
                        }
                        binding.chipRoller.setOnClickListener {
                            viewModel.onRollerClicked()
                        }
                        binding.chipHeater.setOnClickListener {
                            viewModel.onHeaterClicked()
                        }

                        binding.buttonMyAccount.setOnClickListener {
                            findNavController().navigate(R.id.action_HomePageFragment_to_MyAccountFragment)
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