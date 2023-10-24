package com.example.onlineshoppoizon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.onlineshoppoizon.databinding.ActivityMainMenuBinding
import com.example.onlineshoppoizon.databinding.ActivityUserOrdersBinding



class UserOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this@UserOrdersActivity, R.color.white)
    }
}