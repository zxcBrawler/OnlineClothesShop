package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class ProfileRepository @Inject constructor (private val apiInterface: ApiInterface)
    : BaseRepository() {

        suspend fun getUserById(
            token : String,
            id : Long
        )
        = safeApiCall {
            apiInterface.getUserById(token, id)
        }

}