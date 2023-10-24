package com.example.onlineshoppoizon.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.onlineshoppoizon.MainMenuActivity
import com.example.onlineshoppoizon.databinding.FragmentRegistration2Binding
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.viewmodel.RegisterViewModel


class Registration2Fragment : BaseFragment<RegisterViewModel,FragmentRegistration2Binding,RegisterRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle : Bundle? = this.arguments

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val intent = Intent(requireContext(), MainMenuActivity::class.java)
                    startActivity(intent)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(), it.errorCode.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        binding.back.setOnClickListener {
            bundle?.clear()
        }

        binding.loginButton.setOnClickListener {
            //implement
            val profilePhoto = binding.profilePhoto.drawable.toString()
            val gender = binding.gender.selectedItemId
            val phone = binding.phoneNum.text.toString()

            if(!bundle?.isEmpty!!){
                viewModel.register(
                    bundle.getString("email")!!,
                    bundle.getString("password")!!,
                    gender,
                    phone,
                    profilePhoto,
                    bundle.getString("username")!!,
                "")
            }
        }
    }

    override fun getViewModel(): Class<RegisterViewModel> = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistration2Binding = FragmentRegistration2Binding.inflate(inflater, container, false)

    override fun getFragmentRepository(): RegisterRepository = RegisterRepository((requestBuilder.buildRequest(
        ApiInterface::class.java)))
}