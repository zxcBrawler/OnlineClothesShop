package com.example.onlineshoppoizon.model

data class UserAddress(
    val userAddressId: Long = 0,
    val user: User = User(),
    val address: Address = Address(),
)
