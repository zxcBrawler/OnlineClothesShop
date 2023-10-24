package com.example.onlineshoppoizon

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.databinding.ActivityMainBinding
import com.example.onlineshoppoizon.fragments.LoginFragment
import com.example.onlineshoppoizon.repository.MainActivityRepository
import com.example.onlineshoppoizon.repository.MainMenuRepository
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding, MainActivityRepository>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        FragmentHelper.openRegisterFragment(this@MainActivity,R.id.fragmentContainerView, LoginFragment())
    }

    override fun getViewModel(): Class<MainActivityViewModel>
    = MainActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainBinding
    = ActivityMainBinding.inflate(inflater)


    override fun getActivityRepository(): MainActivityRepository {
        return MainActivityRepository(requestBuilder.buildRequest(ApiInterface::class.java))
    }
}