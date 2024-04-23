package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.repository.CatalogueRepository
import com.example.onlineshoppoizon.repository.ChangePasswordRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(private val repository: ChangePasswordRepository)
    : BaseViewModel(repository){

    private val _changePasswordResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    val changePasswordResponse : LiveData<Resource<Any>>
        get() = _changePasswordResponse

    fun changePassword(username : String, newPassword : String) = viewModelScope.launch {
        _changePasswordResponse.value = repository.changePassword(username, newPassword)
    }
}