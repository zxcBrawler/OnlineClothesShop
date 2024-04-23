package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import retrofit2.http.Path
import javax.inject.Inject

class OrderDetailsRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

    suspend fun getOrderDeliveryInfo(
        token : String,
        orderId : Long
    ) =
        safeApiCall {
            apiInterface.getOrderDeliveryInfo(token, orderId)
        }

    suspend fun getOrderComposition(
        token : String,
        orderId: Long
    ) =
        safeApiCall {
            apiInterface.getOrderComposition(token, orderId)
        }

}