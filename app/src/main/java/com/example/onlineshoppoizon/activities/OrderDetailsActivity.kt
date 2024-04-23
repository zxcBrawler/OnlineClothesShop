package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.ItemsPaymentsAdapter
import com.example.onlineshoppoizon.adapters.OrderCompositionAdapter
import com.example.onlineshoppoizon.databinding.ActivityOrderDetailsBinding
import com.example.onlineshoppoizon.model.Clothes
import com.example.onlineshoppoizon.repository.OrderDetailsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.OrderDetailsViewModel

class OrderDetailsActivity : BaseActivity<OrderDetailsViewModel, ActivityOrderDetailsBinding, OrderDetailsRepository>() {
    private lateinit var adapter : OrderCompositionAdapter
    private var token = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent.getIntExtra("id", 0)
        val getUserToken = userPreferences.getToken().asLiveData()

        getUserToken.observe(this){ userToken ->
            token = userToken
            viewModel.getOrderComposition("Bearer $token", intent.toLong())
            viewModel.getOrderDeliveryInfo("Bearer $token", intent.toLong())
        }

        viewModel.compositionResponse.observe(this){
            when(it) {
                is Resource.Success -> {

                    binding.cartItems.layoutManager = LinearLayoutManager(this)
                    adapter = OrderCompositionAdapter(it.value)
                    binding.cartItems.adapter = adapter

                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

                }
            }
        }

        viewModel.deliveryResponse.observe(this){
            when(it) {
                is Resource.Success -> {
                    binding.sumOrderText.text = it.value.order.sumOrder
                    binding.typeDeliveryText.text = it.value.typeDelivery.nameType
                    binding.userCardText.text = it.value.order.userCard.card.cardNum
                    if(it.value.addresses == null){
                        binding.pickUpAddressText.text = it.value.shopAddresses?.shopAddressDirection
                    }
                    else {
                        binding.pickUpAddressText.text = it.value.addresses!!.nameAddress
                    }

                }
                is Resource.Failure -> {
                    Toast.makeText(this,getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()

                }
            }
        }

        binding.back.setOnClickListener {
            finishActivity()
        }

    }

    override fun getViewModel(): Class<OrderDetailsViewModel> =
        OrderDetailsViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityOrderDetailsBinding
    = ActivityOrderDetailsBinding.inflate(inflater)

    override fun getActivityRepository(): OrderDetailsRepository =
        OrderDetailsRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}