package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.onlineshoppoizon.MainMenuActivity
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.FragmentLoginBinding
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.utils.visible
import com.example.onlineshoppoizon.viewmodel.AuthViewModel


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.loading.visible(false)
            when(it){
                is Resource.Success -> {
                    it.value.accessToken?.let { it1 -> viewModel.saveAuthToken(it1) }
                    requireActivity().startNewActivity(MainMenuActivity::class.java)
                    activity?.finish()
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.loginButton.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()
            binding.loading.visible(true)
            viewModel.login(email, password)
        }

        binding.registerButton.setOnClickListener {
            FragmentHelper.openRegisterFragment(requireContext(), R.id.fragmentContainerView, RegisterFragment())
        }
    }

    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): AuthRepository
        = AuthRepository(requestBuilder.buildRequest(ApiInterface::class.java), userPreferences)

}