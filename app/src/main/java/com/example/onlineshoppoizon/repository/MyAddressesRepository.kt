package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MyAddressesRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository (){

        suspend fun getUserAddresses(
            token : String,
            id : Long
        ) =
            safeApiCall {
                apiInterface.getUserAddresses(token, id)
            }

    suspend fun deleteAddress(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.deleteAddress(token, id)
        }
}