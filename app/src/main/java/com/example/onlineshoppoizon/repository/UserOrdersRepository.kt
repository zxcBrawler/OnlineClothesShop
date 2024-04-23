package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class UserOrdersRepository  @Inject constructor (private val apiInterface: ApiInterface)
    : BaseRepository() {

        suspend fun getUserOrders (
            token : String,
            userId : Long
        ) =
            safeApiCall {
                apiInterface.getUserOrders(token, userId)
            }
}