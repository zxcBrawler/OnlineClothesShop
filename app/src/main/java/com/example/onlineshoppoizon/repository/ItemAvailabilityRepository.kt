package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class ItemAvailabilityRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository()  {

        suspend fun getItemAvailability() =
            safeApiCall {
                apiInterface.getItemAvailability()
            }
}