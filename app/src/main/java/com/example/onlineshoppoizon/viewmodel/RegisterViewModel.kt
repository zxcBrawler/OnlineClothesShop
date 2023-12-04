package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.response.RegisterResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterViewModel @Inject constructor (
    private val repository: RegisterRepository
): BaseViewModel(repository) {
    private val _registerResponse : MutableLiveData<Resource<RegisterResponse>> = MutableLiveData()
    val registerResponse : LiveData<Resource<RegisterResponse>>
        get() = _registerResponse

    fun register(
        email: String,
        gender: Long,
        phoneNumber: String,
        profilePhoto: String,
        username: String,
        passwordHash: String
    ) = viewModelScope.launch {
        _registerResponse.value = repository.register(
            email,
            gender,
            phoneNumber,
            profilePhoto,
            username,
            passwordHash)
    }
}