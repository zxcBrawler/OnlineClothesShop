package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MyAddressesRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository (){

        suspend fun getUserAddresses(
            id : Long
        ) =
            safeApiCall {
                apiInterface.getUserAddresses(id)
            }

    suspend fun deleteAddress(
        id : Long
    ) =
        safeApiCall {
            apiInterface.deleteAddress(id)
        }
}