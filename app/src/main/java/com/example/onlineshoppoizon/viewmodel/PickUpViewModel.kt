package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.model.ShopGarnish
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
    private val _cartResponse : MutableLiveData<Resource<List<Cart>>> = MutableLiveData()
    val cartResponse : LiveData<Resource<List<Cart>>>
        get() = _cartResponse

    private val _itemAvailabilityResponse : MutableLiveData<Resource<List<ShopGarnish>>> = MutableLiveData()
    val itemAvailabilityResponse : LiveData<Resource<List<ShopGarnish>>>
        get() = _itemAvailabilityResponse

    fun getItemAvailability(
        token : String,
        colorId : Long,
        sizeId : Long
    ) =
        viewModelScope.launch {
            _itemAvailabilityResponse.value = repository.getItemAvailability(token, colorId, sizeId)
        }


    fun getCart(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.getCartItems(token, id)
        }
}