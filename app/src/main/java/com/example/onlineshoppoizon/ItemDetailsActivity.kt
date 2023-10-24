package com.example.onlineshoppoizon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat

class ItemDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        window.statusBarColor = ContextCompat.getColor(this@ItemDetailsActivity, R.color.white)
    }
}