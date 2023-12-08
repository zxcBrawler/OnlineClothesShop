package com.example.onlineshoppoizon.activities

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
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

            val animation: Animation =
                AlphaAnimation(1f, 0f) //to change visibility from visible to invisible

            animation.duration = 500 //1 second duration for each animation cycle

            animation.interpolator = LinearInterpolator()
            animation.repeatCount = Animation.INFINITE //repeating indefinitely

            animation.repeatMode = Animation.REVERSE //animation will start from end point once ended.

            binding.logo.startAnimation(animation) //

            Handler().postDelayed({
                startNewActivityFromActivity(activity)
            }, 2000)

        }
    }

    override fun getViewModel(): Class<LoadingActivityViewModel>
    = LoadingActivityViewModel::class.java

    override fun getActivityBinding(inflater: LayoutInflater): ActivityLoadingBinding
    = ActivityLoadingBinding.inflate(inflater)

    override fun getActivityRepository(): LoadingRepository
    = LoadingRepository()
}