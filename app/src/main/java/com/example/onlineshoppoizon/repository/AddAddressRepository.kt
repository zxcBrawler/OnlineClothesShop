package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import javax.inject.Inject

class AddAddressRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun addAddress(
           userId: Long,
           city: String,
           nameAddress: String,
           directionAddress: String
        ) =
            safeApiCall {
                apiInterface.addAddress(userId, city, nameAddress, directionAddress)
            }

}