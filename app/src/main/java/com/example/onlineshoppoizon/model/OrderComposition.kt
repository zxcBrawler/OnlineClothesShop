package com.example.onlineshoppoizon.model

data class OrderComposition(
    val orderCompId: Long,
    val clothesComp: Clothes,
    val orderId: Orders,
    val quantity: Int,
    val sizeClothes : SizeClothes ,
    val colorClothes: Colors,
)
