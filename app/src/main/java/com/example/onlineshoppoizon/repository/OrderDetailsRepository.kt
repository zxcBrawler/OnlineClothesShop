package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import retrofit2.http.Path
import javax.inject.Inject

class OrderDetailsRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

    suspend fun getOrderDeliveryInfo(
        orderId : Long
    ) =
        safeApiCall {
            apiInterface.getOrderDeliveryInfo(orderId)
        }

    suspend fun getOrderComposition(
        orderId: Long
    ) =
        safeApiCall {
            apiInterface.getOrderComposition(orderId)
        }

}