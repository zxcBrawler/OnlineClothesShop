package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityMainMenuBinding
import com.example.onlineshoppoizon.fragments.CartFragment
import com.example.onlineshoppoizon.fragments.MainPageFragment
import com.example.onlineshoppoizon.fragments.ProfileFragment
import com.example.onlineshoppoizon.repository.MainMenuRepository
import com.example.onlineshoppoizon.retrofit.ApiInterface
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.viewmodel.MainMenuViewModel

class MainMenuActivity : BaseActivity<MainMenuViewModel, ActivityMainMenuBinding, MainMenuRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentHelper.openFragment(this, R.id.fragmentMainMenu, MainPageFragment())
        changeFragment()
    }

    override fun getViewModel(): Class<MainMenuViewModel>  = MainMenuViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityMainMenuBinding =
        ActivityMainMenuBinding.inflate(inflater)

    override fun getActivityRepository(): MainMenuRepository {
        return MainMenuRepository(requestBuilder.buildRequest(ApiInterface::class.java))
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