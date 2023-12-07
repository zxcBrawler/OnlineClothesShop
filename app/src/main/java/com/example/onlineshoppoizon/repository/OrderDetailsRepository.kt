package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import retrofit2.http.Field
import javax.inject.Inject

class OrderDetailsRepository @Inject constructor(private val apiInterface: ApiInterface)
    : BaseRepository(){

}