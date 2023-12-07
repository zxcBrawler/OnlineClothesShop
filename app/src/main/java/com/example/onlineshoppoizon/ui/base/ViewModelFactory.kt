package com.example.onlineshoppoizon.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.onlineshoppoizon.repository.AddCardRepository
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.repository.CartRepository
import com.example.onlineshoppoizon.repository.ChangeProfileRepository
import com.example.onlineshoppoizon.repository.DeliveryActivityRepository
import com.example.onlineshoppoizon.repository.DeliveryFragmentRepository
import com.example.onlineshoppoizon.repository.ItemAvailabilityRepository
import com.example.onlineshoppoizon.repository.ItemDetailsRepository
import com.example.onlineshoppoizon.repository.LoadingRepository
import com.example.onlineshoppoizon.repository.MainActivityRepository
import com.example.onlineshoppoizon.repository.MainPageRepository
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.repository.MainMenuRepository
import com.example.onlineshoppoizon.repository.MyCardsRepository
import com.example.onlineshoppoizon.repository.OrderDetailsRepository
import com.example.onlineshoppoizon.repository.PaymentRepository
import com.example.onlineshoppoizon.repository.PickUpRepository
import com.example.onlineshoppoizon.repository.UserOrdersRepository
import com.example.onlineshoppoizon.viewmodel.AddCardViewModel
import com.example.onlineshoppoizon.viewmodel.AuthViewModel
import com.example.onlineshoppoizon.viewmodel.CartViewModel
import com.example.onlineshoppoizon.viewmodel.ChangeProfileViewModel
import com.example.onlineshoppoizon.viewmodel.DeliveryActivityViewModel
import com.example.onlineshoppoizon.viewmodel.DeliveryFragmentViewModel
import com.example.onlineshoppoizon.viewmodel.ItemAvailabilityViewModel
import com.example.onlineshoppoizon.viewmodel.ItemDetailsViewModel
import com.example.onlineshoppoizon.viewmodel.LoadingActivityViewModel
import com.example.onlineshoppoizon.viewmodel.MainActivityViewModel
import com.example.onlineshoppoizon.viewmodel.MainMenuViewModel
import com.example.onlineshoppoizon.viewmodel.MainPageViewModel
import com.example.onlineshoppoizon.viewmodel.MyCardsViewModel
import com.example.onlineshoppoizon.viewmodel.OrderDetailsViewModel
import com.example.onlineshoppoizon.viewmodel.PaymentViewModel
import com.example.onlineshoppoizon.viewmodel.PickUpViewModel
import com.example.onlineshoppoizon.viewmodel.ProfileViewModel
import com.example.onlineshoppoizon.viewmodel.RegisterViewModel
import com.example.onlineshoppoizon.viewmodel.UserOrdersViewModel
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
             modelClass.isAssignableFrom(ItemAvailabilityViewModel::class.java) -> ItemAvailabilityViewModel(repository as ItemAvailabilityRepository) as T
             modelClass.isAssignableFrom(CartViewModel::class.java) -> CartViewModel(repository as CartRepository) as T
             modelClass.isAssignableFrom(UserOrdersViewModel::class.java) -> UserOrdersViewModel(repository as UserOrdersRepository) as T
             modelClass.isAssignableFrom(DeliveryActivityViewModel::class.java) -> DeliveryActivityViewModel(repository as DeliveryActivityRepository) as T
             modelClass.isAssignableFrom(PickUpViewModel::class.java) -> PickUpViewModel(repository as PickUpRepository) as T
             modelClass.isAssignableFrom(DeliveryFragmentViewModel::class.java) -> DeliveryFragmentViewModel(repository as DeliveryFragmentRepository) as T
             modelClass.isAssignableFrom(ChangeProfileViewModel::class.java) -> ChangeProfileViewModel(repository as ChangeProfileRepository) as T
             modelClass.isAssignableFrom(AddCardViewModel::class.java) -> AddCardViewModel(repository as AddCardRepository) as T
             modelClass.isAssignableFrom(MyCardsViewModel::class.java) -> MyCardsViewModel(repository as MyCardsRepository) as T
             modelClass.isAssignableFrom(PaymentViewModel::class.java) -> PaymentViewModel(repository as PaymentRepository) as T
             modelClass.isAssignableFrom(OrderDetailsViewModel::class.java) -> OrderDetailsViewModel(repository as OrderDetailsRepository) as T

            else -> throw IllegalArgumentException("ViewModelClass not found")
         }
     }
}