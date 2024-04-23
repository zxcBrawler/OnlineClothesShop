package com.example.onlineshoppoizon.repository

import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import javax.inject.Inject

class AddCardRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){



        suspend fun addCard (
            token: String,
            userId : Long,
            cardNum: String,
            expDate: String,
            cvv: String,
            ) =
            safeApiCall {

                apiInterface.addCard(token, userId, cardNum, expDate, cvv)
            }
}