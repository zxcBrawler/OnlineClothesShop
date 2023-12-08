package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.AddressAdapter
import com.example.onlineshoppoizon.adapters.CardAdapter
import com.example.onlineshoppoizon.databinding.ActivityMyAddressesBinding
import com.example.onlineshoppoizon.model.UserAddress
import com.example.onlineshoppoizon.repository.MyAddressesRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.viewmodel.MyAddressesViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MyAddressesActivity : BaseActivity<MyAddressesViewModel, ActivityMyAddressesBinding, MyAddressesRepository>() {
    var userId : Long = 0
    private lateinit var adapter : AddressAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()
            viewModel.getUserAddresses(userId)
        }

        viewModel.addressResponse.observe(this){
            when(it){
                is Resource.Success -> {

                    val addresses : MutableList<UserAddress> = arrayListOf()

                    addresses.addAll(it.value)
                    binding.recyclerAddresses.layoutManager = LinearLayoutManager(this)
                    adapter = AddressAdapter(addresses)
                    binding.recyclerAddresses.adapter = adapter

                    adapter.setOnItemClickListener(object : AddressAdapter.OnItemClickListener{
                        override fun onItemClick(position: Long) {
                            TODO("Not yet implemented")
                        }

                        override fun onItemDelete(position: Long) {
                            var dialog = MaterialAlertDialogBuilder(this@MyAddressesActivity, R.style.CustomDialogTheme)
                            dialog.setTitle("Delete this address?")
                                .setPositiveButton("Yes"
                                ) {
                                        newDialog, _ ->
                                    newDialog.dismiss()
                                    viewModel.deleteAddress(position)
                                    viewModel.deleteResponse.observe(this@MyAddressesActivity){ its ->
                                        when (its){
                                            is Resource.Success -> {
                                                Toast.makeText(applicationContext, "Successfully deleted", Toast.LENGTH_SHORT).show()
                                                this@MyAddressesActivity.finish()
                                            }
                                            is Resource.Failure -> {

                                            }
                                        }
                                    }

                                }.setNegativeButton("No"
                                ) { newDialog, _ ->
                                    newDialog.dismiss()
                                }
                                .show()
                        }

                    })
                }
                is Resource.Failure -> {

                }
            }
        }

        binding.addAddress.setOnClickListener {
            val activity = AddAddressActivity::class.java
            startNewActivityFromActivity(activity)
        }

        binding.back.setOnClickListener {
            finishActivity()
        }

    }

    override fun getViewModel(): Class<MyAddressesViewModel> =
        MyAddressesViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMyAddressesBinding =
        ActivityMyAddressesBinding.inflate(inflater)

    override fun getActivityRepository(): MyAddressesRepository =
        MyAddressesRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}