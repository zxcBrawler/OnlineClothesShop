package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PickUpViewModel @Inject constructor(private val repository: PickUpRepository)
    : BaseViewModel(repository) {

    private val _shopsResponse : MutableLiveData<Resource<List<ShopAddresses>>> = MutableLiveData()
    val shopsResponse : LiveData<Resource<List<ShopAddresses>>>
        get() = _shopsResponse

    fun getShops( token : String,) =
        viewModelScope.launch {
            _shopsResponse.value = repository.getShops(token)
        }
}