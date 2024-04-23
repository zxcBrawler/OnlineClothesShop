package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Cart
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.model.ClothesColors
import com.example.onlineshoppoizon.model.ClothesSizeClothes
import com.example.onlineshoppoizon.model.PhotosOfClothes
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.response.CartResponse
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

    private val _cartResponse : MutableLiveData<Resource<CartResponse>> = MutableLiveData()
    val cartResponse : LiveData<Resource<CartResponse>>
        get() = _cartResponse

    private val _updateResponse : MutableLiveData<Resource<List<Cart>>> = MutableLiveData()
    val updateResponse : LiveData<Resource<List<Cart>>>
        get() = _updateResponse

    private val _existsResponse : MutableLiveData<Resource<Long>> = MutableLiveData()
    val existsResponse : LiveData<Resource<Long>>
        get() = _existsResponse


    fun getClothesById(id : Int,  token : String,) =
        viewModelScope.launch {
            _clothesByIdResponse.value = repository.getClothesById(id, token)
        }
    fun getPhotos(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _clothesPhotoResponse.value = repository.getClothesPhoto(token, id)
        }
    fun getClothesColors(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _clothesColorsResponse.value = repository.getClothesColors(token, id)
        }
    fun getClothesSizes(
        token : String,
        id : Long
    ) =
        viewModelScope.launch {
            _clothesSizesResponse.value = repository.getClothesSizes(token, id)
        }

    fun addToCart(
        token : String,
        user: Int,
        colorClothes: Int,
        quantity: Int,
        sizeClothes: Int
    ) =
        viewModelScope.launch {
            _cartResponse.value = repository.
            addToCart(token, user, colorClothes, quantity, sizeClothes)
        }

    fun checkIfItemExistsInCart (
        token : String,
        size : Long,
        color: Long,
        user : Long,
        clothes : Long
    ) =
        viewModelScope.launch {
            _existsResponse.value =
            repository.checkIfItemExistsInCart(token, size, color, user, clothes)
        }

    fun updateQuantity(
        token : String,
        id : Long,
        updateType : Int,
        userId : Long
    ) =
        viewModelScope.launch {
            _updateResponse.value = repository.updateQuantity(token, id, updateType, userId)
        }
}