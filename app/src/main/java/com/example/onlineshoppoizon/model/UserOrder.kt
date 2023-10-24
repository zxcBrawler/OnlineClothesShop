package com.example.onlineshoppoizon.model

data class UserOrder(
    val userOrderId: Long,
    val user: User,
    val orders: Orders,
)
