package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityMainBinding
import com.example.onlineshoppoizon.fragments.LoginFragment
import com.example.onlineshoppoizon.repository.MainActivityRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding, MainActivityRepository>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentHelper.openRegisterFragment(
            this@MainActivity,
            R.id.fragmentContainerView,
            LoginFragment()
        )
        val permissions = arrayOf(
            android.Manifest.permission.READ_MEDIA_IMAGES,
        )
        ActivityCompat.requestPermissions(
            this,
            permissions,
            0
        )
    }

    override fun getViewModel(): Class<MainActivityViewModel>
    = MainActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainBinding
    = ActivityMainBinding.inflate(inflater)


    override fun getActivityRepository(): MainActivityRepository {
        return MainActivityRepository(requestBuilder.buildRequest(ApiInterface::class.java))
    }
}