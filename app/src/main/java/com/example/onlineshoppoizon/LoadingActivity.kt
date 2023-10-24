package com.example.onlineshoppoizon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.databinding.ActivityLoadingBinding
import com.example.onlineshoppoizon.repository.LoadingRepository
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.startNewActivity
import com.example.onlineshoppoizon.viewmodel.LoadingActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
class LoadingActivity : BaseActivity<LoadingActivityViewModel, ActivityLoadingBinding, LoadingRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val userPreferences = UserPreferences(this)
        userPreferences.authToken.asLiveData().observe(this, Observer {
            val activity =  if (it != null) MainMenuActivity::class.java else MainActivity::class.java
            startNewActivity(activity)
        })
    }

    override fun getViewModel(): Class<LoadingActivityViewModel>
    = LoadingActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityLoadingBinding
    = ActivityLoadingBinding.inflate(inflater)

    override fun getActivityRepository(): LoadingRepository
    = LoadingRepository()
}