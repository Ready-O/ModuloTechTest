package com.reda.modulotechtest.ui.account

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
import com.reda.modulotechtest.R
import com.reda.modulotechtest.databinding.FragmentMyAccountBinding
import com.reda.modulotechtest.model.Address
import com.reda.modulotechtest.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment : Fragment() {

    private val viewModel: MyAccountViewModel by viewModels()

    private var _binding: FragmentMyAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.coroutineScope.launchWhenStarted {
            viewModel.viewState.collect {
                when(it){
                    is MyAccountViewState.Display ->  {
                        binding.displayUser.visibility = VISIBLE
                        binding.editUser.visibility = GONE

                        binding.displayFirstName.text =  it.user.firstName
                        binding.displayLastName.text = it.user.lastName
                        binding.displayBirthDate.text = it.user.birthDate
                        binding.displayCity.text = it.user.address.city
                        binding.displayPostalCode.text = it.user.address.postalCode
                        binding.displayStreet.text = it.user.address.street
                        binding.displayStreetCode.text = it.user.address.streetCode
                        binding.displayCountry.text = it.user.address.country

                        binding.accountCta.text = getString(R.string.edit_account)
                        binding.accountCta.setOnClickListener {
                            viewModel.editState()
                        }
                    }

                    is MyAccountViewState.Edit -> {
                        binding.displayUser.visibility = GONE
                        binding.editUser.visibility = VISIBLE

                        binding.editFirstName.setText(it.user.firstName)
                        binding.editLastName.setText(it.user.lastName)
                        binding.editBirthDate.setText(it.user.birthDate)
                        binding.editCity.setText(it.user.address.city)
                        binding.editPostalCode.setText(it.user.address.postalCode)
                        binding.editStreet.setText(it.user.address.street)
                        binding.editStreetCode.setText(it.user.address.streetCode)
                        binding.editCountry.setText(it.user.address.country)

                        binding.accountCta.text = getString(R.string.confirm)
                        binding.accountCta.setOnClickListener {
                            viewModel.editAccount(
                                User(
                                    firstName = binding.editFirstName.text.toString(),
                                    lastName = binding.editLastName.text.toString(),
                                    birthDate = binding.editBirthDate.text.toString(),
                                    address = Address(
                                        city = binding.editCity.text.toString(),
                                        postalCode = binding.editPostalCode.text.toString(),
                                        street = binding.editStreet.text.toString(),
                                        streetCode = binding.editStreetCode.text.toString(),
                                        country = binding.editCountry.text.toString()
                                    )
                                )
                            )
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