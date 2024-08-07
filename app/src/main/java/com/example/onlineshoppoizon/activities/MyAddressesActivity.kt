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
    private var token = ""
    private lateinit var adapter : AddressAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getUserToken = userPreferences.getToken().asLiveData()

        userPreferences.get().asLiveData().observe(this){
            userId = it.toLong()

        }
        getUserToken.observe(this){ userToken ->
            token = userToken
            viewModel.getUserAddresses("Bearer $token", userId)
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
                        }

                        override fun onItemDelete(position: Long) {
                            var dialog = MaterialAlertDialogBuilder(this@MyAddressesActivity, R.style.CustomDialogTheme)
                            dialog.setTitle(getString(R.string.delete_this_address))
                                .setPositiveButton(
                                    getString(R.string.yes)
                                ) {
                                        newDialog, _ ->
                                    newDialog.dismiss()
                                    getUserToken.observe(this@MyAddressesActivity){ userToken ->
                                        token = userToken
                                        viewModel.deleteAddress("Bearer $token", position)
                                        viewModel.deleteResponse.observe(this@MyAddressesActivity){ its ->
                                            when (its){
                                                is Resource.Success -> {
                                                    Toast.makeText(applicationContext,
                                                        getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show()
                                                    this@MyAddressesActivity.finish()
                                                }
                                                is Resource.Failure -> {
                                                    Toast.makeText(applicationContext,
                                                        getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show()
                                                    this@MyAddressesActivity.finish()
                                                }
                                            }
                                        }
                                    }



                                }.setNegativeButton(
                                    getString(R.string.no)
                                ) { newDialog, _ ->
                                    newDialog.dismiss()
                                }
                                .show()
                        }

                    })
                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

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