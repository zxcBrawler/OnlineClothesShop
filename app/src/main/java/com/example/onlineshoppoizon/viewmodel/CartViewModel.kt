package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: CartRepository)
    : BaseViewModel(repository) {
}