package com.example.onlineshoppoizon.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.onlineshoppoizon.ChangeProfileActivity
import com.example.onlineshoppoizon.MainActivity
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.UserOrdersActivity
import com.example.onlineshoppoizon.databinding.FragmentProfileBinding
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment: BaseFragment<ProfileViewModel,FragmentProfileBinding,ProfileRepository> () {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersLayout.setOnClickListener {
            val intent = Intent(context, UserOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.changeProfileLayout.setOnClickListener {
            val intent = Intent(context, ChangeProfileActivity::class.java)
            startActivity(intent)
        }

        binding.endSession.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        lifecycleScope.launch {
            userPreferences.clear()
        }
        requireActivity().startNewActivity(MainActivity::class.java)
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