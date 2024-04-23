package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.model.TypeClothes
import com.example.onlineshoppoizon.repository.TypeClothesRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TypeClothesViewModel @Inject constructor(private val repository: TypeClothesRepository)
    : BaseViewModel(repository){

    private val _typeClothesResponse : MutableLiveData<Resource<List<TypeClothes>>> = MutableLiveData()
    val typeClothesResponse : LiveData<Resource<List<TypeClothes>>>
        get() = _typeClothesResponse

    fun getTypeClothes (
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _typeClothesResponse.value = repository.getTypeClothes(token, id)
        }
}