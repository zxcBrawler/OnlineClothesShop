package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class UserOrdersRepository  @Inject constructor (private val apiInterface: ApiInterface)
    : BaseRepository() {

        suspend fun getUserOrders (
            userId : Long
        ) =
            safeApiCall {
                apiInterface.getUserOrders(userId)
            }
}