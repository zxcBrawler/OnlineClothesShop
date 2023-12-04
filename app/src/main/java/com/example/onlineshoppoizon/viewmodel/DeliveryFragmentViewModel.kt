package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.DeliveryFragmentRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class DeliveryFragmentViewModel @Inject constructor(private val repository: DeliveryFragmentRepository)
    : BaseViewModel(repository)  {
}