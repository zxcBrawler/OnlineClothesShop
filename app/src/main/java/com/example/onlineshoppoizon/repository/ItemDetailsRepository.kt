package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class ItemDetailsRepository
@Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
        suspend fun getClothesById(id : Int) = safeApiCall {
            apiInterface.getClothesById(id)
        }
    suspend fun getClothesPhoto(
        id : Long
    ) =
        safeApiCall {
            apiInterface.getClothesPhotos(id)
        }
    suspend fun getClothesSizes(
        id : Long
    ) =
        safeApiCall {
            apiInterface.getSizes(id)
        }
    suspend fun getClothesColors(
        id : Long
    ) =
        safeApiCall {
            apiInterface.getColorsClothes(id)
        }
    suspend fun addToCart(
        userId: Int,
        colorClothesId : Int,
        quantity : Int,
        sizeClothesId : Int
    ) =
        safeApiCall {
            apiInterface.addToCart(userId, colorClothesId, sizeClothesId, quantity)
        }
}