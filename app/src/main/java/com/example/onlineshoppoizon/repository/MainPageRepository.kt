package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MainPageRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
    suspend fun getClothes(token : String) = safeApiCall {
        apiInterface.getClothes(token)
    }
    suspend fun getCartItems(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getCart(token, id)
        }

}