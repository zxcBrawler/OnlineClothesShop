package com.example.onlineshoppoizon.ui.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel (
    private val repository: BaseRepository
) : ViewModel(){
}