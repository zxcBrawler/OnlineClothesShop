package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Message
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.PaymentRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class PaymentViewModel @Inject constructor(private val repository: PaymentRepository)
    : BaseViewModel(repository){
    private val _orderResponse : MutableLiveData<Resource<Message>> = MutableLiveData()
    val orderResponse : LiveData<Resource<Message>>
        get() = _orderResponse

    private val _cardResponse : MutableLiveData<Resource<List<UserCard>>> = MutableLiveData()
    val cardResponse : LiveData<Resource<List<UserCard>>>
        get() = _cardResponse
    private val _cartResponse : MutableLiveData<Resource<List<Cart>>> = MutableLiveData()
    val cartResponse : LiveData<Resource<List<Cart>>>
        get() = _cartResponse

    private val _deleteResponse : MutableLiveData<Resource<Message>> = MutableLiveData()
    val deleteResponse : LiveData<Resource<Message>>
        get() = _deleteResponse

    fun clearUserCart(
        id : Long
    ) =
        viewModelScope.launch {
            _deleteResponse.value = repository.clearUserCart(id)
        }

    fun getUserCards(
        id : Long
    ) =
        viewModelScope.launch {
            _cardResponse.value = repository.getUserCards(id)
        }
    fun getCart(
        id : Long
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.getCartItems(id)
        }

    fun placeNewOrder(
        sumOrder : String,
        userCardId : Long,
        typeDelivery : Long,
        shopAddress : Long?,
        userAddress : Long?,
        orderComp : List<Long>
    ) =
        viewModelScope.launch {
            _orderResponse.value = repository.placeNewOrder(sumOrder, userCardId, typeDelivery, shopAddress, userAddress, orderComp)
        }
}