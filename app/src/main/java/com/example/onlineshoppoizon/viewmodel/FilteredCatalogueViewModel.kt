package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.model.TypeClothes
import com.example.onlineshoppoizon.repository.FilteredCatalogueRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilteredCatalogueViewModel @Inject constructor(private val repository: FilteredCatalogueRepository)
    : BaseViewModel(repository) {
    private val _typeClothesResponse : MutableLiveData<Resource<List<Clothes>>> = MutableLiveData()
    val typeClothesResponse : LiveData<Resource<List<Clothes>>>
        get() = _typeClothesResponse

    fun getTypeClothes(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _typeClothesResponse.value = repository.getTypeClothes(token, id)
        }
}