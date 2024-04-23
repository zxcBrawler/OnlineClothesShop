package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

    suspend fun getUserCards(
        token : String,
        id : Long,
    ) =
        safeApiCall {
            apiInterface.getUserCards(token, id)
        }

    suspend fun clearUserCart(
        token : String,
        id : Long,
    )
    =
        safeApiCall {
            apiInterface.clearUserCart(token, id)
        }
    suspend fun getCartItems(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getCart(token, id)
        }
    suspend fun placeNewOrder(
        token : String,
        sumOrder : String,
        userCardId : Long,
        typeDelivery : Long,
        shopAddress : Long?,
        userAddress : Long?,
        orderComp : List<Long>
    ) =
        safeApiCall {
            apiInterface.placeNewOrder(token, sumOrder, userCardId, typeDelivery, shopAddress, userAddress, orderComp)
        }
}