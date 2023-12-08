package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Message
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.MyAddressesRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyAddressesViewModel @Inject constructor(private val repository: MyAddressesRepository)
    : BaseViewModel(repository){
    private val _addressResponse : MutableLiveData<Resource<List<UserAddress>>> = MutableLiveData()
    val addressResponse : LiveData<Resource<List<UserAddress>>>
        get() = _addressResponse

    private val _deleteResponse : MutableLiveData<Resource<Message>> = MutableLiveData()
    val deleteResponse : LiveData<Resource<Message>>
        get() = _deleteResponse

        fun getUserAddresses(
            id : Long
        ) =
            viewModelScope.launch {
                _addressResponse.value = repository.getUserAddresses(id)

            }

    fun deleteAddress (
        id : Long
    ) =
        viewModelScope.launch {
            _deleteResponse.value = repository.deleteAddress(id)
        }
}