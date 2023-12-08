package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.repository.DeliveryFragmentRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveryFragmentViewModel @Inject constructor(private val repository: DeliveryFragmentRepository)
    : BaseViewModel(repository)  {
    private val _addressResponse : MutableLiveData<Resource<List<UserAddress>>> = MutableLiveData()
    val addressResponse : LiveData<Resource<List<UserAddress>>>
        get() = _addressResponse

    fun getUserAddresses(
        id : Long
    ) =
        viewModelScope.launch {
            _addressResponse.value = repository.getUserAddresses(id)

        }
}