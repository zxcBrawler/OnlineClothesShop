package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class ItemDetailsRepository
@Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository() {
        suspend fun getClothesById(id : Int, token : String) = safeApiCall {
            apiInterface.getClothesById(token, id)
        }
    suspend fun getClothesPhoto(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getClothesPhotos(token, id)
        }
    suspend fun getClothesSizes(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getSizes(token, id)
        }
    suspend fun getClothesColors(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.getColorsClothes(token, id)
        }
    suspend fun addToCart(
        token : String,
        userId: Int,
        colorClothesId : Int,
        quantity : Int,
        sizeClothesId : Int
    ) =
        safeApiCall {
            apiInterface.addToCart(token, userId, colorClothesId, sizeClothesId, quantity)
        }

    suspend fun checkIfItemExistsInCart(
        token : String,
        size : Long,
        color: Long,
        user : Long,
        clothes : Long
    ) =
        safeApiCall {
            apiInterface.checkIfItemExistsInCart(token, size, color, user, clothes)
        }

    suspend fun updateQuantity(
        token : String,
        id : Long,
        updateType : Int,
        userId: Long
    ) =
        safeApiCall {
            apiInterface.updateQuantity(token, id, updateType, userId)
        }
}