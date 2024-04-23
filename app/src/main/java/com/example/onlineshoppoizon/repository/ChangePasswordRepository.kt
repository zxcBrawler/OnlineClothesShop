package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class ChangePasswordRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

    suspend fun changePassword(username : String, newPassword : String) =
        safeApiCall {
            apiInterface.changePassword(username, newPassword)
        }
}