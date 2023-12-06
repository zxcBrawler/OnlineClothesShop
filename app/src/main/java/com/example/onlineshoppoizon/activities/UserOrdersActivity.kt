package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityUserOrdersBinding
import com.example.onlineshoppoizon.repository.UserOrdersRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.viewmodel.UserOrdersViewModel

class UserOrdersActivity : BaseActivity<UserOrdersViewModel, ActivityUserOrdersBinding, UserOrdersRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("id", 0)
        viewModel.getUserOrders(userId.toLong())
        viewModel.userOrderResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    Toast.makeText(this, "+", Toast.LENGTH_SHORT).show()
                    
                }
                is Resource.Failure -> {
                    Toast.makeText(this, "-", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getViewModel(): Class<UserOrdersViewModel>  =
        UserOrdersViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityUserOrdersBinding =
        ActivityUserOrdersBinding.inflate(inflater)

    override fun getActivityRepository(): UserOrdersRepository =
        UserOrdersRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}