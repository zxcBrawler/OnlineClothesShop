package com.example.onlineshoppoizon.model

data class Cart(
    val id: Long,
    val user: User,
    val clothes: Clothes,
    val quantity: String,
)
