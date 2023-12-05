package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import retrofit2.http.Path
import javax.inject.Inject

class ChangeProfileRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
    suspend fun getUserById(
        id : Long
    )
            = safeApiCall {
        apiInterface.getUserById(id)
    }
        suspend fun changeProfile(
           id : Long,
           email: String,
           passwordHash: String,
           gender: Long,
           phoneNumber: String,
           profilePhoto: String,
           username: String
        ) =
            safeApiCall {
                apiInterface
                    .changeProfile(
                        id,
                        email,
                        passwordHash,
                        gender,
                        phoneNumber,
                        profilePhoto,
                        username)
            }
}