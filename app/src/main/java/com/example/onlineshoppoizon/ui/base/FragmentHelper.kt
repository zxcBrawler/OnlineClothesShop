package com.example.onlineshoppoizon.ui.base

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.onlineshoppoizon.R
object FragmentHelper {
    private fun getFragmentManager(context: Context): FragmentManager {
        return (context as AppCompatActivity).supportFragmentManager
    }

    fun openRegisterFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
            .setCustomAnimations(R.anim.enter_register, R.anim.exit_register,R.anim.pop_enter_register, R.anim.pop_exit_register)
            .replace(frameId, fragment, fragment.activity.toString())
            .addToBackStack(null).commit()
    }

    fun openFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(frameId, fragment, fragment.activity.toString())
            .addToBackStack(null).commit()
    }

    fun backToLoginFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
            .setCustomAnimations(R.anim.pop_enter_register, R.anim.pop_exit_register,R.anim.enter_register, R.anim.exit_register)
            .replace(frameId, fragment, fragment.activity.toString())
            .addToBackStack(null).commit()
    }

    fun addFragment(context: Context, frameId: Int, fragment: Fragment) {
        getFragmentManager(context).beginTransaction()
            .add(frameId, fragment, fragment.activity.toString())
            .addToBackStack(null).commit()
    }
}