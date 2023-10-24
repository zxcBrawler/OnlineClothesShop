package com.example.onlineshoppoizon.viewmodel

import androidx.lifecycle.ViewModel
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class ProfileViewModel @Inject constructor (
    private val repository: ProfileRepository
): BaseViewModel(repository) {
}