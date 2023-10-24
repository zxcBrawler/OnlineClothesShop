package com.example.onlineshoppoizon.model

data class ShopGarnish(
    val shopGarnishId: Long,
    val clothes: Clothes,
    val shopAddresses: ShopAddresses,
    val quantity: Int,
)
