package com.reda.modulotechtest.model

data class User(
    val firstName: String,
    val lastName: String,
    val address: Address,
    val birthDate: String
)

data class Address(
    val city: String,
    val postalCode: String,
    val street: String,
    val streetCode: String,
    val country: String
)