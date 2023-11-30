package com.example.onlineshoppoizon.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment

fun<A : Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}
fun<A : Activity> Fragment.startNewActivity(activity: Class<A>, id: Int){
    Intent(requireContext(), activity).also {
        it.putExtra("id", id)
        startActivity(it)
    }
}


fun View.visible(isVisible : Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

