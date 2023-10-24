package com.example.onlineshoppoizon.response

import com.example.onlineshoppoizon.model.User

data class LoginResponse(
    val user: User,
    val createdAt: String,
    val accessToken: String?,
)
