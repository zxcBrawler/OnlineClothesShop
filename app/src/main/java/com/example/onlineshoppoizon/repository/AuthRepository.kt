package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class AuthRepository @Inject constructor (
    private val api: ApiInterface,
    private val userPreferences: UserPreferences,
    )
    : BaseRepository(){
        suspend fun login(
            email: String,
            password: String
        ) = safeApiCall {
            api.login(email, password)
        }

    suspend fun saveAuthToken(token: String){
        userPreferences.saveAuthToken(token)
    }
}