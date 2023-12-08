package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
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

    fun getCart(
        id : Long
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.getCartItems(id)
        }

    fun deleteFromCart(
        id : Long,
        userId : Long
    ) = viewModelScope.launch {
        _cartResponse.value = repository.deleteFromCart(id, userId)
    }

    fun updateQuantity(
        id : Long,
        updateType : Int,
        userId : Long,
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.updateQuantity(id, updateType, userId)
        }
}