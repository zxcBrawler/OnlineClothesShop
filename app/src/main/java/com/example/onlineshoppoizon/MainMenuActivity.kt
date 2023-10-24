package com.example.onlineshoppoizon

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.onlineshoppoizon.databinding.ActivityMainMenuBinding
import com.example.onlineshoppoizon.fragments.CartFragment
import com.example.onlineshoppoizon.fragments.MainPageFragment
import com.example.onlineshoppoizon.fragments.ProfileFragment
import com.example.onlineshoppoizon.repository.UserRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.retrofit.Resource
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.MainMenuViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class MainMenuActivity : BaseActivity<MainMenuViewModel, ActivityMainMenuBinding, UserRepository>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO("fix user authentication")
        viewModel.getUser()
        viewModel.user.observe(this, Observer {
            when(it){
                is Resource.Success -> {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }
                is Resource.Failure -> {
                    Toast.makeText(this, it.errorCode.toString(), Toast.LENGTH_SHORT).show()
                }
            }

        })

        window.statusBarColor = ContextCompat.getColor(this@MainMenuActivity, R.color.dark_brown)
        FragmentHelper.openFragment(this, R.id.fragmentMainMenu, MainPageFragment())
        changeFragment()
    }

    override fun getViewModel(): Class<MainMenuViewModel>  = MainMenuViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainMenuBinding =
        ActivityMainMenuBinding.inflate(inflater)

    override fun getActivityRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first()}
        return UserRepository(requestBuilder.buildRequest(ApiInterface::class.java, token))
    }

    private fun changeFragment(){
        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->{
                    FragmentHelper.openFragment(this, R.id.fragmentMainMenu, MainPageFragment())
                }
                R.id.catalogue ->{
                }
                R.id.profile ->{
                    FragmentHelper.openFragment(this, R.id.fragmentMainMenu, ProfileFragment())
                }
                R.id.cart ->{
                    FragmentHelper.openFragment(this, R.id.fragmentMainMenu, CartFragment())
                }
                else -> {
                    FragmentHelper.openFragment(this, R.id.fragmentMainMenu, MainPageFragment())
                }
            }
            true
        }
    }
}