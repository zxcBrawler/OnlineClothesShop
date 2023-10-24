package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class RegisterRepository @Inject constructor (private val apiInterface: ApiInterface)
    : BaseRepository(){

   suspend fun register(
        email: String,
        password: String,
        gender: Long,
        phoneNumber: String,
        profilePhoto: String,
        username: String,
        passwordHash: String
    ) = safeApiCall {
        apiInterface.register(
            email,
            password,
            passwordHash,
            gender,
            phoneNumber,
            profilePhoto,
            username
            )
    }
}