package com.example.onlineshoppoizon.model

data class ShopGarnish(
    val shopGarnishId: Long,
    val colorClothesGarnish: ClothesColors,
    val sizeClothesGarnish: ClothesSizeClothes,
    val shopAddressesGarnish: ShopAddresses,
    val quantity: Int,
)
