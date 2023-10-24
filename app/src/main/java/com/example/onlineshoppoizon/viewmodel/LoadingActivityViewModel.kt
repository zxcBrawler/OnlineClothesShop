package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.LoadingRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class LoadingActivityViewModel @Inject constructor(private val repository: LoadingRepository)
    : BaseViewModel(repository) {
}