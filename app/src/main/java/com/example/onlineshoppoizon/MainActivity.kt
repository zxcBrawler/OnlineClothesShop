package com.example.onlineshoppoizon

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.example.onlineshoppoizon.fragments.LoginFragment
import com.example.onlineshoppoizon.response.UserPreferences
import com.example.onlineshoppoizon.ui.base.FragmentHelper
import com.example.onlineshoppoizon.utils.startNewActivity
import dagger.hilt.android.AndroidEntryPoint


class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.white)
        FragmentHelper.openRegisterFragment(this@MainActivity,R.id.fragmentContainerView, LoginFragment())

        val userPreferences  = UserPreferences(this)

        userPreferences.authToken.asLiveData().observe(this, Observer {
            Toast.makeText(this, it ?: "Token is null", Toast.LENGTH_SHORT).show()
        })
    }
}