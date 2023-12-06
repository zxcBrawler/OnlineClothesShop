package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import javax.inject.Inject

class AddCardRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun addCard (
            userId : Long,
            cardNum: String,
            expDate: String,
            cvv: String,
            ) =
            safeApiCall {
                apiInterface.addCard(userId, cardNum, expDate, cvv)
            }
}