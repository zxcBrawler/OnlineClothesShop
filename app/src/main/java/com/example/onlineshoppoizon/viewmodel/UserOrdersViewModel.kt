package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.UserOrder
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.repository.UserOrdersRepository
import com.example.onlineshoppoizon.response.RegisterResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserOrdersViewModel  @Inject constructor (
    private val repository: UserOrdersRepository
) : BaseViewModel(repository) {

    private val _userOrderResponse : MutableLiveData<Resource<List<UserOrder>>> = MutableLiveData()
    val userOrderResponse : LiveData<Resource<List<UserOrder>>>
        get() = _userOrderResponse

    fun getUserOrders (
        token : String,
        userId : Long
    ) =
        viewModelScope.launch {
            _userOrderResponse.value = repository.getUserOrders(token, userId)
        }

}