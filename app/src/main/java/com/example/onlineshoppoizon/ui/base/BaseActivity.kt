package com.example.onlineshoppoizon.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.retrofit.RequestBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseActivity<vm : ViewModel, b : ViewBinding, r: BaseRepository> : AppCompatActivity(){

    protected lateinit var binding: b
    protected lateinit var viewModel: vm
    protected val requestBuilder = RequestBuilder()
    protected lateinit var userPreferences: UserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userPreferences = UserPreferences(this)
        binding = getActivityBinding(layoutInflater)
        val factory = ViewModelFactory(getActivityRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        setContentView(binding.root)
        lifecycleScope.launch {
            userPreferences.authToken.first()
        }
    }

    abstract fun getViewModel() : Class<vm>
    abstract fun getActivityBinding(inflater: LayoutInflater): b
    abstract fun getActivityRepository() : r
}