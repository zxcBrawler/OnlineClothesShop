package com.example.onlineshoppoizon.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.activities.MainActivity
import com.example.onlineshoppoizon.activities.MainMenuActivity
import com.example.onlineshoppoizon.databinding.FragmentRegistration2Binding
import com.example.onlineshoppoizon.repository.RegisterRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseFragment
import com.example.onlineshoppoizon.utils.RealPathUtil
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch


class Registration2Fragment : BaseFragment<RegisterViewModel,FragmentRegistration2Binding,RegisterRepository>() {
    private var path : String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle : Bundle? = this.arguments

        viewModel.registerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    requireActivity().startNewActivityFromActivity(MainActivity::class.java)
                }

                is Resource.Failure -> {
                    Toast.makeText(requireContext(),getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.back.setOnClickListener {
            bundle?.clear()
        }

        binding.profilePhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            resultLauncher.launch(intent)
        }

        binding.loginButton.setOnClickListener {
            val profilePhoto = path
            val gender = binding.gender.selectedItemId
            val phone = binding.phoneNum.text.toString()

            if(!bundle?.isEmpty!!){
                viewModel.register(
                    bundle.getString("email")!!,
                    gender,
                    phone,
                    profilePhoto,
                    bundle.getString("username")!!,
                    bundle.getString("password")!!,)
                Toast.makeText(requireContext(),
                    getString(R.string.successfully_registered), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getViewModel(): Class<RegisterViewModel>
    = RegisterViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegistration2Binding
    = FragmentRegistration2Binding.inflate(inflater, container, false)

    override fun getFragmentRepository(): RegisterRepository
    = RegisterRepository((requestBuilder.buildRequest(
        ApiInterface::class.java)))



    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri: Uri? = result.data?.data
            path = RealPathUtil.getRealPathFromURI(requireContext(), uri!!)!!
            binding.profilePhoto.setImageURI(uri)

        }
    }
}