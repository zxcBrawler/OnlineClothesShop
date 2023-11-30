package com.example.onlineshoppoizon.response

import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.model.User

data class LoginResponse(
    val user: User = User(),
    val createdAt: String = "",
    val accessToken: String? = "",
)
