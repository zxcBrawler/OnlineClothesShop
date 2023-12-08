package com.example.onlineshoppoizon.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.FragmentRegisterBinding
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.response.RegisterResponse
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.RegisterViewModel


class RegisterFragment : BaseFragment<RegisterViewModel,FragmentRegisterBinding,RegisterRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.back.setOnClickListener {
            FragmentHelper.backToLoginFragment(requireContext(), R.id.fragmentContainerView, LoginFragment())
        }
        binding.additionalInfoButton.setOnClickListener {
            FragmentHelper.openRegisterFragment(requireContext(), R.id.fragmentContainerView, Registration2Fragment())
        }
        binding.additionalInfoButton.setOnClickListener {
            val email: String = binding.email.text.toString().trim()
            val username: String = binding.username.text.toString().trim()
            val password: String = binding.password.text.toString().trim()

            if(email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()){
                val bundle = Bundle()
                bundle.putString("email",email)
                bundle.putString("username",username)
                bundle.putString("password",password)
                val secondFragment = Registration2Fragment()
                secondFragment.arguments = bundle
                FragmentHelper.openRegisterFragment(requireContext(), R.id.fragmentContainerView,secondFragment)
            }
            else {
                Toast.makeText(requireContext(),
                    getString(R.string.fill_out_all_fields), Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getViewModel(): Class<RegisterViewModel>
    = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding
    = FragmentRegisterBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): RegisterRepository
    = RegisterRepository((requestBuilder.buildRequest(ApiInterface::class.java)))
}