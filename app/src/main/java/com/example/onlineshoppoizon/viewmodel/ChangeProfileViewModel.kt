package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.User
import com.example.onlineshoppoizon.repository.ChangeProfileRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangeProfileViewModel @Inject constructor(private val repository: ChangeProfileRepository)
    : BaseViewModel(repository) {
    private val _userResponse : MutableLiveData<Resource<User>> = MutableLiveData()
    val userResponse : LiveData<Resource<User>>
        get() = _userResponse

    private val _profileResponse : MutableLiveData<Resource<User>> = MutableLiveData()
    val profileResponse : LiveData<Resource<User>>
        get() = _profileResponse

    fun getUserById (
        id : Long
    ) = viewModelScope.launch {
        _profileResponse.value = repository.getUserById(id)
    }

        fun changeProfile (
            id : Long,
            email: String,
            passwordHash: String,
            gender: Long,
            phoneNumber: String,
            profilePhoto: String,
            username: String
        ) =
            viewModelScope.launch {
                _userResponse.value = repository
                    .changeProfile(id,
                        email,
                        passwordHash,
                        gender,
                        phoneNumber,
                        profilePhoto,
                        username)
            }
}