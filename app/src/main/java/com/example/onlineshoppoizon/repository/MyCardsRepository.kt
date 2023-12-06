package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MyCardsRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun getUserCards(
            id : Long,
        ) =
            safeApiCall {
                apiInterface.getUserCards(id)
            }

    suspend fun deleteCard(
        id : Long
    ) =
        safeApiCall {
            apiInterface.deleteCard(id)
        }
}