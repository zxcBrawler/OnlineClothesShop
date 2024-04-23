package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class DeliveryFragmentRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository (){

    suspend fun getUserAddresses(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getUserAddresses(token,id)
        }
}