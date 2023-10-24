package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.repository.UserRepository
import com.example.onlineshoppoizon.response.LoginResponse
import com.example.onlineshoppoizon.retrofit.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(private val repository: UserRepository)
    : ViewModel(){

        private val _user : MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
        val user: LiveData<Resource<LoginResponse>>
            get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = repository.getUser()
    }
}