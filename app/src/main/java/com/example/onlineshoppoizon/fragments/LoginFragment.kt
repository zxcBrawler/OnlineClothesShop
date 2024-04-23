package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.onlineshoppoizon.activities.MainMenuActivity
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.ForgotPasswordActivity
import com.example.onlineshoppoizon.databinding.FragmentLoginBinding
import com.example.onlineshoppoizon.repository.AuthRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.EmailValidator
import com.example.onlineshoppoizon.utils.PasswordValidator
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.utils.startNewActivityFromFragment
import com.example.onlineshoppoizon.utils.visible
import com.example.onlineshoppoizon.viewmodel.AuthViewModel
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner) {
            binding.loading.visible(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        userPreferences.saveAuthToken(it.value.accessToken ?: "",it.value.user.id.toInt(), it.value.user.gender.nameCategory)
                    }
                    requireActivity().startNewActivityFromActivity(MainMenuActivity::class.java)
                    activity?.finish()
                }

                is Resource.Failure -> {
                        Toast.makeText(requireContext(),
                            getString(R.string.invalid_email_or_password), Toast.LENGTH_SHORT)
                            .show()

                }
            }
        }

        binding.loginButton.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()
            binding.loading.visible(true)
            viewModel.login(username, password)
           // if(EmailValidator.isValidEmail(email) && PasswordValidator.validatePassword(password).isSuccess){

           // }
           /* else {
                Toast.makeText(requireContext(),
                    getString(R.string.invalid_email_format), Toast.LENGTH_SHORT).show()
            }*/

        }

        binding.registerButton.setOnClickListener {
            FragmentHelper.openRegisterFragment(requireContext(), R.id.fragmentContainerView, RegisterFragment())
        }

        binding.forgotPassword.setOnClickListener {
            startNewActivityFromFragment(ForgotPasswordActivity::class.java)
        }
    }

    override fun getViewModel(): Class<AuthViewModel> = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater,container,false)

    override fun getFragmentRepository(): AuthRepository
        = AuthRepository(requestBuilder.buildRequest(ApiInterface::class.java))

}