package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class PaymentRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

    suspend fun getUserCards(
        id : Long,
    ) =
        safeApiCall {
            apiInterface.getUserCards(id)
        }

    suspend fun clearUserCart(
        id : Long,
    )
    =
        safeApiCall {
            apiInterface.clearUserCart(id)
        }
    suspend fun getCartItems(
        id : Long
    ) =
        safeApiCall {
            apiInterface.getCart(id)
        }
    suspend fun placeNewOrder(
        sumOrder : String,
        userCardId : Long,
        typeDelivery : Long,
        shopAddress : Long?,
        userAddress : Long?,
        orderComp : List<Long>
    ) =
        safeApiCall {
            apiInterface.placeNewOrder(sumOrder, userCardId, typeDelivery, shopAddress, userAddress, orderComp)
        }
}