package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlineshoppoizon.repository.CartRepository
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: CartRepository)
    : ViewModel() {
}