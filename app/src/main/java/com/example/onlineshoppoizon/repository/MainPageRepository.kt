package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MainPageRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
    suspend fun getClothes() = safeApiCall {
        apiInterface.getClothes()
    }
    suspend fun getCartItems(
        id : Long
    ) =
        safeApiCall {
            apiInterface.getCart(id)
        }

}