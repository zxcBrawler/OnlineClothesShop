package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MyCardsRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun getUserCards(
            token : String,
            id : Long,
        ) =
            safeApiCall {
                apiInterface.getUserCards(token, id)
            }


    suspend fun deleteCard(
        token : String,
        id : Long
    ) =
        safeApiCall {
            apiInterface.deleteCard(token, id)
        }
}