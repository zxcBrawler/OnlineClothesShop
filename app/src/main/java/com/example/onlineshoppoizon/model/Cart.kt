package com.example.onlineshoppoizon.model

data class Cart(
    val id: Long,
    val user: User,
    val colorClothes: ClothesColors,
    val sizeClothes: ClothesSizeClothes,
    val quantity: String,
)
