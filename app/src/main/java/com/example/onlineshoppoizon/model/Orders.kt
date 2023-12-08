package com.example.onlineshoppoizon.model

data class Orders(
    val idOrder: Long = 0,
    val numberOrder: String = "",
    val timeOrder: String = "",
    val dateOrder: String = "",
    val sumOrder: String = "",
    val currentStatus: StatusOrder = StatusOrder(),
    var userCard: UserCard = UserCard(),
)
