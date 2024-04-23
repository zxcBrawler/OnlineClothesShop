package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val api: ApiInterface,
    )
    : BaseRepository(){
        suspend fun login(
            username: String,
            passwordHash: String
        ) = safeApiCall {
            api.login(username, passwordHash)
        }

}