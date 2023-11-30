package com.example.onlineshoppoizon.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.onlineshoppoizon.ChangeProfileActivity
import com.example.onlineshoppoizon.MainActivity
import com.example.onlineshoppoizon.UserOrdersActivity
import com.example.onlineshoppoizon.databinding.FragmentProfileBinding
import com.example.onlineshoppoizon.repository.ProfileRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileFragment: BaseFragment<ProfileViewModel,FragmentProfileBinding,ProfileRepository> () {

    private var userId = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getUserId = userPreferences.get().asLiveData()

        getUserId.observe(viewLifecycleOwner, Observer {
            userId = it
            viewModel.getUserById(userId.toLong())
        })

        viewModel.profileResponse.observe(viewLifecycleOwner, Observer {
            when(it){
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
        })

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