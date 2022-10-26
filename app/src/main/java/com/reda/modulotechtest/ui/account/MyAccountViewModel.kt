package com.reda.modulotechtest.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reda.modulotechtest.model.User
import com.reda.modulotechtest.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    private val _viewState: MutableStateFlow<MyAccountViewState> = MutableStateFlow(MyAccountViewState.Loading)
    val viewState: StateFlow<MyAccountViewState> = _viewState.asStateFlow()

    init{
        viewModelScope.launch{
            userRepository.getUser().collectLatest { result ->
                result.onSuccess {
                    _viewState.value = MyAccountViewState.Display(it)
                }
                    .onFailure {
                        _viewState.value = MyAccountViewState.Error(it)
                    }
            }
        }
    }

    fun editState(){
        viewModelScope.launch{
            val user = (viewState.value as MyAccountViewState.Display).user
            _viewState.value = MyAccountViewState.Edit(user)
        }
    }

    fun editAccount(user: User){
        viewModelScope.launch{
            userRepository.updateUser(user)
        }
    }
}