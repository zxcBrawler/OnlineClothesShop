package com.example.onlineshoppoizon.response

import com.example.onlineshoppoizon.model.CategoryClothes


data class RegisterResponse(
    var username: String,
    var password: String,
    var passwordHash: String,
    var email: String = "",
    val phoneNumber: String = "",
    val gender: CategoryClothes = CategoryClothes(0,""),
    val profilePhoto: String = "",
)
