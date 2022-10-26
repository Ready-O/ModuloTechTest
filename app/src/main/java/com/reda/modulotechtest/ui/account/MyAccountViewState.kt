package com.reda.modulotechtest.ui.account

import com.reda.modulotechtest.model.User

interface MyAccountViewState {

    object Loading: MyAccountViewState

    data class Display(val user: User): MyAccountViewState

    data class Edit(val user: User): MyAccountViewState

    data class Error(val throwable: Throwable): MyAccountViewState
}