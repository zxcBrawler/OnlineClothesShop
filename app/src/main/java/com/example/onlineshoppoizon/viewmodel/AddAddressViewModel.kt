package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.repository.AddAddressRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddAddressViewModel @Inject constructor(private val repository: AddAddressRepository)
    :BaseViewModel (repository)  {
    private val _addressResponse : MutableLiveData<Resource<UserAddress>> = MutableLiveData()
    val addressResponse : LiveData<Resource<UserAddress>>
        get() = _addressResponse
        fun addAddress(
            token : String,
            userId: Long,
            city: String,
            nameAddress: String,
            directionAddress: String
        ) =
            viewModelScope.launch {
                _addressResponse.value = repository.addAddress(token,userId, city, nameAddress, directionAddress)
            }
}