package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.adapters.ItemAvailabilityAdapter
import com.example.onlineshoppoizon.adapters.OrderAdapter
import com.example.onlineshoppoizon.databinding.ActivityUserOrdersBinding
import com.example.onlineshoppoizon.repository.UserOrdersRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.startNewActivityWithId
import com.example.onlineshoppoizon.viewmodel.UserOrdersViewModel

class UserOrdersActivity : BaseActivity<UserOrdersViewModel, ActivityUserOrdersBinding, UserOrdersRepository>() {

    private lateinit var adapter : OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("id", 0)
        viewModel.getUserOrders(userId.toLong())
        viewModel.userOrderResponse.observe(this){
            when(it){
                is Resource.Success -> {
                    binding.recyclerOrders.layoutManager = LinearLayoutManager(this)
                    adapter = OrderAdapter(it.value)
                    binding.recyclerOrders.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        OrderAdapter.OnItemClickListener {
                        override fun onItemClick(position: Long) {
                            val activity = OrderDetailsActivity::class.java
                            startNewActivityWithId(activity, position.toInt())

                        }
                    })
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