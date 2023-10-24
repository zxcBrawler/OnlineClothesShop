package com.example.onlineshoppoizon.model

data class UserAddress(
    val userAddressId: Long,
    val user: User,
    val address: Address,
)
