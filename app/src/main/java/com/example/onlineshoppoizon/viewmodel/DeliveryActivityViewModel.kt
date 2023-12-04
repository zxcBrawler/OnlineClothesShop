package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.DeliveryActivityRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class DeliveryActivityViewModel @Inject constructor(private val repository: DeliveryActivityRepository)
    : BaseViewModel(repository){

}