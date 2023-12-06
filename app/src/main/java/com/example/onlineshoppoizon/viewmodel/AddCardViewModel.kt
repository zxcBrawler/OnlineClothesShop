package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.UserCard
import com.example.onlineshoppoizon.repository.AddCardRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddCardViewModel @Inject constructor(private val repository: AddCardRepository)
    : BaseViewModel (repository){

    private val _cardResponse : MutableLiveData<Resource<UserCard>> = MutableLiveData()
    val cardResponse : LiveData<Resource<UserCard>>
        get() = _cardResponse
        fun addUserCard(
            userId : Long,
            cardNum: String,
            expDate: String,
            cvv: String,
        ) =
            viewModelScope.launch {
                _cardResponse.value = repository
                    .addCard(userId, cardNum, expDate, cvv)
            }

}