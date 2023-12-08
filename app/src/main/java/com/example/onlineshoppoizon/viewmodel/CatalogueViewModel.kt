package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.CategoryClothes
import com.example.onlineshoppoizon.repository.CatalogueRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CatalogueViewModel @Inject constructor(private val repository: CatalogueRepository)
    : BaseViewModel(repository){

    private val _catalogueResponse : MutableLiveData<Resource<List<CategoryClothes>>> = MutableLiveData()
    val catalogueResponse : LiveData<Resource<List<CategoryClothes>>>
        get() = _catalogueResponse

    fun getCategories() = viewModelScope.launch {
       _catalogueResponse.value = repository.getCategories()
    }
}