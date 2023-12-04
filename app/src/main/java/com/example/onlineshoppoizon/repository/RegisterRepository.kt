package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class RegisterRepository @Inject constructor (private val apiInterface: ApiInterface)
    : BaseRepository(){

   suspend fun register(
        email: String,
        gender: Long,
        phoneNumber: String,
        profilePhoto: String,
        username: String,
        passwordHash: String
    ) = safeApiCall {
        apiInterface.register(
            email,
            passwordHash,
            gender,
            phoneNumber,
            profilePhoto,
            username
            )
    }
}