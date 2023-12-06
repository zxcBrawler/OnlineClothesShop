package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.R
import com.example.onlineshoppoizon.databinding.ActivityLoadingBinding
import com.example.onlineshoppoizon.repository.LoadingRepository
import com.example.onlineshoppoizon.ui.base.BaseActivity
import com.example.onlineshoppoizon.utils.startNewActivityFromActivity
import com.example.onlineshoppoizon.viewmodel.LoadingActivityViewModel

//@AndroidEntryPoint
class LoadingActivity : BaseActivity<LoadingActivityViewModel, ActivityLoadingBinding, LoadingRepository>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getUserId = userPreferences.get().asLiveData()
        getUserId.observe(this) {
            val activity =
                if (it != -1) MainMenuActivity::class.java else MainActivity::class.java
                startNewActivityFromActivity(activity)
        }
    }

    override fun getViewModel(): Class<LoadingActivityViewModel>
    = LoadingActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityLoadingBinding
    = ActivityLoadingBinding.inflate(inflater)

    override fun getActivityRepository(): LoadingRepository
    = LoadingRepository()
}