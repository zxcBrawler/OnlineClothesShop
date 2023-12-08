package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.DeliveryInfo
import com.example.onlineshoppoizon.model.Message
import com.example.onlineshoppoizon.model.OrderComposition
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.OrderDetailsRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(private val repository: OrderDetailsRepository)
    : BaseViewModel (repository){

    private val _deliveryResponse : MutableLiveData<Resource<DeliveryInfo>> = MutableLiveData()
    val deliveryResponse : LiveData<Resource<DeliveryInfo>>
        get() = _deliveryResponse

    private val _compositionResponse : MutableLiveData<Resource<List<OrderComposition>>> = MutableLiveData()
    val compositionResponse : LiveData<Resource<List<OrderComposition>>>
        get() = _compositionResponse

    fun getOrderDeliveryInfo(
        orderId : Long
    ) =
        viewModelScope.launch {
            _deliveryResponse.value = repository.getOrderDeliveryInfo(orderId)
        }
    fun getOrderComposition (
        orderId : Long
    ) =
        viewModelScope.launch {
            _compositionResponse.value = repository.getOrderComposition(orderId)
        }

}