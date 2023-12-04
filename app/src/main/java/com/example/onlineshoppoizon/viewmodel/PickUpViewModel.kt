package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class PickUpViewModel @Inject constructor(private val repository: PickUpRepository)
    : BaseViewModel(repository) {
}