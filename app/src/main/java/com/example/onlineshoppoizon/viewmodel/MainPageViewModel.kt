package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.response.ClothesResponse
import com.example.onlineshoppoizon.response.PhotosOfClothesResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPageViewModel @Inject constructor (
    private val repository: MainPageRepository
): BaseViewModel(repository) {
    private val _clothesResponse : MutableLiveData<Resource<List<Clothes>>> = MutableLiveData()
    val clothesResponse : LiveData<Resource<List<Clothes>>>
        get() = _clothesResponse

    fun getClothes()
    = viewModelScope.launch {
        _clothesResponse.value = repository.getClothes()
    }
}