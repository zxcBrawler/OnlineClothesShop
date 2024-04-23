package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.User
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.response.RegisterResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor (
    private val repository: ProfileRepository
): BaseViewModel(repository) {
    private val _profileResponse : MutableLiveData<Resource<User>> = MutableLiveData()
    val profileResponse : LiveData<Resource<User>>
        get() = _profileResponse

    fun getUserById (
        token : String,
        id : Long
    ) = viewModelScope.launch {
            _profileResponse.value = repository.getUserById(token, id)
        }
}