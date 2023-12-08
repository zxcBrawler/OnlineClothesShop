package com.example.onlineshoppoizon.model

data class UserCard(
    val id: Long = 0,
    val user: User = User(),
    val card: Card = Card(),
)
