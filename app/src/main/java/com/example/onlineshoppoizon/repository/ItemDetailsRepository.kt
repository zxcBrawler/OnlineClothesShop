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
    suspend fun getClothesPhoto() =
        safeApiCall {
            apiInterface.getClothesPhotos()
        }
    suspend fun getClothesSizes() =
        safeApiCall {
            apiInterface.getSizes()
        }
    suspend fun getClothesColors() =
        safeApiCall {
            apiInterface.getColorsClothes()
        }
}