package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class CatalogueRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

        suspend fun getCategories(token : String) =
            safeApiCall {
                apiInterface.getCategories(token)
            }
}