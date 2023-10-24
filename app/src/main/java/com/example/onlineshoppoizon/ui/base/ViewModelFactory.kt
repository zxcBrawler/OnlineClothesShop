package com.example.onlineshoppoizon.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.repository.LoadingRepository
import com.example.onlineshoppoizon.repository.MainActivityRepository
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.repository.MainMenuRepository
import com.example.onlineshoppoizon.viewmodel.AuthViewModel
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import com.example.onlineshoppoizon.viewmodel.ItemDetailsViewModel
import com.example.onlineshoppoizon.viewmodel.LoadingActivityViewModel
import com.example.onlineshoppoizon.viewmodel.MainActivityViewModel
import com.example.onlineshoppoizon.viewmodel.MainMenuViewModel
import com.example.onlineshoppoizon.viewmodel.MainPageViewModel
import com.example.onlineshoppoizon.viewmodel.ProfileViewModel
import com.example.onlineshoppoizon.viewmodel.RegisterViewModel
import java.lang.IllegalArgumentException
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor (
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory()
 {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         return when{
             //create this line for every viewmodel in project
             modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
             modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository as RegisterRepository) as T
             modelClass.isAssignableFrom(MainPageViewModel::class.java) -> MainPageViewModel(repository as MainPageRepository) as T
             modelClass.isAssignableFrom(CartViewModel::class.java) -> CartViewModel(repository as CartRepository) as T
             modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileRepository) as T
             modelClass.isAssignableFrom(MainMenuViewModel::class.java) -> MainMenuViewModel(repository as MainMenuRepository) as T
             modelClass.isAssignableFrom(LoadingActivityViewModel::class.java) -> LoadingActivityViewModel(repository as LoadingRepository) as T
             modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel(repository as MainActivityRepository) as T
             modelClass.isAssignableFrom(ItemDetailsViewModel::class.java) -> ItemDetailsViewModel(repository as ItemDetailsRepository) as T

            else -> throw IllegalArgumentException("ViewModelClass not found")
         }
     }
}