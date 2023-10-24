package com.example.onlineshoppoizon.model

data class Orders(
    val idOrder: Long,
    val numberOrder: String,
    val timeOrder: String,
    val dateOrder: String,
    val sumOrder: String,
    val currentStatus: StatusOrder,
)
