package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class CartRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
        suspend fun getCartItems(
            token: String,
            id : Long
        ) =
            safeApiCall {
                apiInterface.getCart(token, id)
            }

        suspend fun deleteFromCart(
            token: String,
            id : Long,
            userId : Long
        ) =
            safeApiCall {
                apiInterface.deleteItem(token, id, userId)
            }

        suspend fun updateQuantity(
            token: String,
            id : Long,
            updateType : Int,
            userId : Long
        ) =
            safeApiCall {
                apiInterface.updateQuantity(token, id, updateType, userId)
            }
    suspend fun getShops(token : String) =
        safeApiCall {
            apiInterface.getShops(token)
        }
    suspend fun clearUserCart(
        token : String,
        id : Long,
    )
            =
        safeApiCall {
            apiInterface.clearUserCart(token, id)
        }
}