package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
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

    suspend fun checkIfItemExistsInCart(
        size : Long,
        color: Long,
        user : Long,
        clothes : Long
    ) =
        safeApiCall {
            apiInterface.checkIfItemExistsInCart(size, color, user, clothes)
        }

    suspend fun updateQuantity(
        id : Long,
        updateType : Int,
        userId: Long
    ) =
        safeApiCall {
            apiInterface.updateQuantity(id, updateType, userId)
        }
}