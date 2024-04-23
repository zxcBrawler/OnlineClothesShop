package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import javax.inject.Inject

class AddAddressRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun addAddress(
            token : String,
           userId: Long,
           city: String,
           nameAddress: String,
           directionAddress: String
        ) =
            safeApiCall {
                apiInterface.addAddress(token,userId, city, nameAddress, directionAddress)
            }

}