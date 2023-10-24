package com.example.onlineshoppoizon.model

data class OrderComposition(
    val orderCompId: Long,
    val clothes: Clothes,
    val orderId: Orders,
    val quantity: Int,
)
