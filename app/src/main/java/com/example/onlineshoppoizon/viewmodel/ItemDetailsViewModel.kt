package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.PhotosOfClothes
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.response.LoginResponse
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemDetailsViewModel @Inject constructor(
    private val repository: ItemDetailsRepository
) : BaseViewModel(repository) {
    private val _clothesByIdResponse : MutableLiveData<Resource<Clothes>> = MutableLiveData()
    val clothesByIdResponse : LiveData<Resource<Clothes>>
        get() = _clothesByIdResponse

    private val _clothesPhotoResponse : MutableLiveData<Resource<List<PhotosOfClothes>>> = MutableLiveData()
    val clothesPhotoResponse : LiveData<Resource<List<PhotosOfClothes>>>
        get() = _clothesPhotoResponse

    private val _clothesSizesResponse : MutableLiveData<Resource<List<ClothesSizeClothes>>> = MutableLiveData()
    val clothesSizesResponse : LiveData<Resource<List<ClothesSizeClothes>>>
        get() = _clothesSizesResponse

    private val _clothesColorsResponse : MutableLiveData<Resource<List<ClothesColors>>> = MutableLiveData()
    val clothesColorsResponse : LiveData<Resource<List<ClothesColors>>>
        get() = _clothesColorsResponse

    fun getClothesById(id : Int) =
        viewModelScope.launch {
            _clothesByIdResponse.value = repository.getClothesById(id)
        }
    fun getPhotos() =
        viewModelScope.launch {
            _clothesPhotoResponse.value = repository.getClothesPhoto()
        }
    fun getClothesColors() =
        viewModelScope.launch {
            _clothesColorsResponse.value = repository.getClothesColors()
        }
    fun getClothesSizes() =
        viewModelScope.launch {
            _clothesSizesResponse.value = repository.getClothesSizes()
        }
}