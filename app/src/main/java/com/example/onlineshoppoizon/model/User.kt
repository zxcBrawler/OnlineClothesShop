package com.example.onlineshoppoizon.model


data class User(
    val id: Long,
    val username: String,
    val password: String,
    val passwordHash: String,
    val email: String,
    val phoneNumber: String,
    val gender: Char,
    val profilePhoto: String,
)