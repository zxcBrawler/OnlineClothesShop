package com.example.onlineshoppoizon.model

data class OrderStatusLog(
    val logId: Long,
    val status: StatusOrder,
    val orders: Orders,
    val timestamp: String
)
