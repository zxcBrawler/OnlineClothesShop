package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Message
import com.example.onlineshoppoizon.model.ShopAddresses
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: CartRepository)
    : BaseViewModel(repository) {

        private val _cartResponse : MutableLiveData<Resource<List<Cart>>> = MutableLiveData()
        val cartResponse : LiveData<Resource<List<Cart>>>
            get() = _cartResponse

    private val _shopsResponse : MutableLiveData<Resource<List<ShopAddresses>>> = MutableLiveData()
    val shopsResponse : LiveData<Resource<List<ShopAddresses>>>
        get() = _shopsResponse

    private val _deleteResponse : MutableLiveData<Resource<Message>> = MutableLiveData()
    val deleteResponse : LiveData<Resource<Message>>
        get() = _deleteResponse

    fun getCart(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.getCartItems(token, id)
        }
    fun getShops( token : String,) =
        viewModelScope.launch {
            _shopsResponse.value = repository.getShops(token)
        }
    fun clearUserCart(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _deleteResponse.value = repository.clearUserCart(token, id)
        }


    fun deleteFromCart(
        token : String,
        id : Long,
        userId : Long
    ) = viewModelScope.launch {
        _cartResponse.value = repository.deleteFromCart(token, id, userId)
    }

    fun updateQuantity(
        token : String,
        id : Long,
        updateType : Int,
        userId : Long,
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.updateQuantity(token, id, updateType, userId)
        }
}