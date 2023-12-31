package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityDeliveryBinding
import com.example.onlineshoppoizon.fragments.PickUpFragment
import com.example.onlineshoppoizon.repository.DeliveryActivityRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.finishActivity
import com.example.onlineshoppoizon.viewmodel.DeliveryActivityViewModel

class DeliveryActivity : BaseActivity<DeliveryActivityViewModel, ActivityDeliveryBinding, DeliveryActivityRepository> () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentHelper.openFragment(this, R.id.delivery_container, PickUpFragment())
        binding.back.setOnClickListener {
            finishActivity()
        }
    }
    fun getCartSum() = intent.getDoubleExtra("sum", 0.0)

    override fun getViewModel(): Class<DeliveryActivityViewModel>  =
        DeliveryActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityDeliveryBinding =
        ActivityDeliveryBinding.inflate(inflater)

    override fun getActivityRepository(): DeliveryActivityRepository =
        DeliveryActivityRepository(requestBuilder.buildRequest(ApiInterface::class.java))
}