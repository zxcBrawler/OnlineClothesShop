package com.example.onlineshoppoizon.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.response.LoginResponse
import com.example.onlineshoppoizon.retrofit.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor (
    private val repository: AuthRepository
): ViewModel() {
    private val _loginResponse : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse : LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value =  repository.login(email, password)
    }

    fun saveAuthToken(token: String){
        viewModelScope.launch {
            repository.saveAuthToken(token)
        }
    }
}