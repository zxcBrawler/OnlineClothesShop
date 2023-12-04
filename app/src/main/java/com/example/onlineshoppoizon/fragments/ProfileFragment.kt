package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.onlineshoppoizon.activities.ChangeProfileActivity
import com.example.onlineshoppoizon.activities.MainActivity
import com.example.onlineshoppoizon.activities.UserOrdersActivity
import com.example.onlineshoppoizon.databinding.FragmentProfileBinding
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.utils.startNewActivityFromFragment
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment: BaseFragment<ProfileViewModel,FragmentProfileBinding,ProfileRepository> () {

    private var userId = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getUserId = userPreferences.get().asLiveData()

        getUserId.observe(viewLifecycleOwner) {
            userId = it
            viewModel.getUserById(userId.toLong())
        }

        viewModel.profileResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.username.text = it.value.username
                    binding.email.text = it.value.email
                    binding.gender.text = it.value.gender.nameCategory
                    Toast.makeText(context, userId.toString(), Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {
                    Toast.makeText(context, "-", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.ordersLayout.setOnClickListener {
            val activity = UserOrdersActivity::class.java
            startNewActivityWithId(activity, userId)
        }

        binding.changeProfileLayout.setOnClickListener {
            val activity = ChangeProfileActivity::class.java
            startNewActivityFromFragment(activity)
        }

        binding.myPaymentMethods.setOnClickListener {
            val activity = ChangeProfileActivity::class.java
            startNewActivityFromFragment(activity)
        }

        binding.endSession.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        lifecycleScope.launch {
           userPreferences.clear()
        }
        requireActivity().startNewActivityFromActivity(MainActivity::class.java)
        requireActivity().finish()
    }

    override fun getViewModel(): Class<ProfileViewModel> =
        ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding =
        FragmentProfileBinding.inflate(layoutInflater)

    override fun getFragmentRepository(): ProfileRepository  =
        ProfileRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}