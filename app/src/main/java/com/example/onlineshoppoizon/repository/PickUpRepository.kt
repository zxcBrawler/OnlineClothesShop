package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class PickUpRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository (){


    suspend fun getCartItems(
        token: String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getCart(token, id)
        }

    suspend fun getItemAvailability(
        token: String,
        colorId : Long,
        sizeId : Long
    ) =
        safeApiCall {
            apiInterface.getItemAvailability(token, colorId, sizeId)
        }

}