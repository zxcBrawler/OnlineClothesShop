package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.repository.MainPageRepository
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

    private val _cartResponse : MutableLiveData<Resource<List<Cart>>> = MutableLiveData()
    val cartResponse : LiveData<Resource<List<Cart>>>
        get() = _cartResponse

    fun getClothes()
    = viewModelScope.launch {
        _clothesResponse.value = repository.getClothes()
    }

    fun getCart(
        id : Long
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.getCartItems(id)
        }
}