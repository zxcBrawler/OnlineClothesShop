package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.model.ShopGarnish
import com.example.onlineshoppoizon.repository.ItemAvailabilityRepository
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ItemAvailabilityViewModel @Inject constructor(
    private val repository: ItemAvailabilityRepository
) : BaseViewModel(repository) {
    private val _itemAvailabilityResponse : MutableLiveData<Resource<List<ShopGarnish>>> = MutableLiveData()
    val itemAvailabilityResponse : LiveData<Resource<List<ShopGarnish>>>
        get() = _itemAvailabilityResponse

    fun getItemAvailability(
        colorId : Long,
        sizeId : Long
    ) =
        viewModelScope.launch {
            _itemAvailabilityResponse.value = repository.getItemAvailability(colorId, sizeId)
        }
}