package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.MainActivityRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val repository: MainActivityRepository
) : BaseViewModel(repository) {
}