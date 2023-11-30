package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
        suspend fun getCartItems(
            id : Long
        ) =
            safeApiCall {
                apiInterface.getCart(id)
            }

        suspend fun deleteFromCart(
            id : Long
        ) =
            safeApiCall {
                apiInterface.deleteItem(id)
            }

        suspend fun updateQuantity(
            id : Long,
            updateType : Int
        ) =
            safeApiCall {
                apiInterface.updateQuantity(id, updateType)
            }
}