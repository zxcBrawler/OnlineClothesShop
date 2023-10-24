package com.example.onlineshoppoizon.repository

import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseRepository
import javax.inject.Inject

class MainMenuRepository @Inject constructor (private val api: ApiInterface)
    : BaseRepository() {

}