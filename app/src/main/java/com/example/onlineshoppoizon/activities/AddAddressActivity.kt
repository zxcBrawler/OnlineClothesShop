package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityAddAddressBinding
import com.example.onlineshoppoizon.repository.AddAddressRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.AddAddressViewModel

class AddAddressActivity : BaseActivity<AddAddressViewModel, ActivityAddAddressBinding, AddAddressRepository>() {
    private var userId : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()
        }

        //implement null check on fields
        binding.addAddress.setOnClickListener {
            val city = binding.city.text.toString()
            val nameAddress = binding.nameAddress.text.toString()
            val directionAddress = binding.directionAddress.text.toString()
            viewModel.addAddress(userId, city, nameAddress, directionAddress)
            viewModel.addressResponse.observe(this){
                when(it){
                    is Resource.Success -> {
                        Toast.makeText(this, "+", Toast.LENGTH_SHORT).show()
                        this.finish()
                    }
                    is Resource.Failure -> {
                        Toast.makeText(this, "-", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }



    }

    override fun getViewModel(): Class<AddAddressViewModel> =
        AddAddressViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityAddAddressBinding =
        ActivityAddAddressBinding.inflate(inflater)

    override fun getActivityRepository(): AddAddressRepository =
        AddAddressRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}