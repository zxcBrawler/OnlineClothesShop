package com.example.onlineshoppoizon.model


data class User(
    val id: Long = 0,
    val username: String = "",
    val password: String = "",
    val passwordHash: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val gender: CategoryClothes = CategoryClothes(),
    val profilePhoto: String = "",
)