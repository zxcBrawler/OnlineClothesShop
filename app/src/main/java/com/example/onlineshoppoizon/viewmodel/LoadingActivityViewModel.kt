package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlineshoppoizon.repository.LoadingRepository
import javax.inject.Inject

class LoadingActivityViewModel @Inject constructor(private val repository: LoadingRepository) :  ViewModel() {
}