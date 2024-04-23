package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Message
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.MyCardsRepository
import com.example.onlineshoppoizon.response.LoginResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MyCardsViewModel @Inject constructor(private val repository: MyCardsRepository)
    : BaseViewModel (repository){
    private val _cardResponse : MutableLiveData<Resource<List<UserCard>>> = MutableLiveData()
    val cardResponse : LiveData<Resource<List<UserCard>>>
        get() = _cardResponse

    private val _deleteResponse : MutableLiveData<Resource<Message>> = MutableLiveData()
    val deleteResponse : LiveData<Resource<Message>>
        get() = _deleteResponse
        fun getUserCards(
            token : String,
            id : Long
        ) =
            viewModelScope.launch {
                _cardResponse.value = repository.getUserCards(token, id)
            }

        fun deleteCard (
            token : String,
            id : Long
        ) =
            viewModelScope.launch {
                _deleteResponse.value = repository.deleteCard(token, id)
            }
}