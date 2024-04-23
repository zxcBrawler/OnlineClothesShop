package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import retrofit2.http.Path
import javax.inject.Inject

class ChangeProfileRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
    suspend fun getUserById(
        id : Long,
        token: String
    )
            = safeApiCall {
        apiInterface.getUserById(token, id)
    }
        suspend fun changeProfile(
            token: String,
           id : Long,
           email: String,
           passwordHash: String,
           gender: Long,
           phoneNumber: String,
           profilePhoto: String,
           username: String,
            role: Long
        ) =
            safeApiCall {
                apiInterface
                    .changeProfile(
                        token,
                        id,
                        email,
                       passwordHash,
                        gender,
                        phoneNumber,
                        profilePhoto,
                        username, role)
            }
}