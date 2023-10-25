package com.example.onlineshoppoizon.model

data class ShopGarnish(
    val shopGarnishId: Long,
    val colorClothes: ClothesColors,
    val sizeClothes: ClothesSizeClothes,
    val shopAddresses: ShopAddresses,
    val quantity: Int,
)
