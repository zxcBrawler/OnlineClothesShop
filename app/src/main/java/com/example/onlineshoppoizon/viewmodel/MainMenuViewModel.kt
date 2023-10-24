package com.example.onlineshoppoizon.viewmodel

import com.example.onlineshoppoizon.repository.MainMenuRepository
import com.example.onlineshoppoizon.ui.base.BaseViewModel
import javax.inject.Inject

class MainMenuViewModel @Inject constructor(private val repository: MainMenuRepository)
    : BaseViewModel(repository){
}