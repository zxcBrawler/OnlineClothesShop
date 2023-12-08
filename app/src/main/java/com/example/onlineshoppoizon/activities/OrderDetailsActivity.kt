package com.example.onlineshoppoizon.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityOrderDetailsBinding
import com.example.onlineshoppoizon.repository.OrderDetailsRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.OrderDetailsViewModel

class OrderDetailsActivity : BaseActivity<OrderDetailsViewModel, ActivityOrderDetailsBinding, OrderDetailsRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent.getIntExtra("id", 0)

        viewModel.getOrderComposition(intent.toLong())
        viewModel.compositionResponse.observe(this){
            when(it) {
                is Resource.Success -> {

                }
                is Resource.Failure -> {

                }
            }
        }
        viewModel.getOrderDeliveryInfo(intent.toLong())
        viewModel.deliveryResponse.observe(this){
            when(it) {
                is Resource.Success -> {

                }
                is Resource.Failure -> {

                }
            }
        }

    }

    override fun getViewModel(): Class<OrderDetailsViewModel> =
        OrderDetailsViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityOrderDetailsBinding
    = ActivityOrderDetailsBinding.inflate(inflater)

    override fun getActivityRepository(): OrderDetailsRepository =
        OrderDetailsRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}