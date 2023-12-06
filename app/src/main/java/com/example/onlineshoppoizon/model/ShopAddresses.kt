package com.example.onlineshoppoizon.model

data class ShopAddresses(
    val shopAddressId: Long,
    val shopAddressDirection: String,
    val shopMetro: String,
    val latitude: String,
    val longitude: String,
    val contactNumber: String
)
