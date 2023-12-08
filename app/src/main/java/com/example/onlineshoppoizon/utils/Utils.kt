package com.example.onlineshoppoizon.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment

fun<A : Activity> Activity.startNewActivityFromActivity(activity: Class<A>){
    Intent(this, activity).also {
        startActivity(it)
    }
}
fun<A : Activity> Activity.startNewActivityWithId(activity: Class<A>, id: Int){
    Intent(this, activity).also {
        it.putExtra("id", id)
        startActivity(it)
    }
}
fun<A : Activity> Fragment.startNewActivityWithDeliveryPickUpInfo
            (activity: Class<A>, typeDelivery: Long, shopAddress : Long, sum : Double){
    Intent(requireContext(), activity).also {
        it.putExtra("typeDelivery", typeDelivery)
        it.putExtra("shopAddress", shopAddress)
        it.putExtra("sum", sum)
        startActivity(it)
    }
}
fun<A : Activity> Fragment.startNewActivityWithDeliveryHomeInfo
            (activity: Class<A>, typeDelivery: Long, address : Long, sum : Double){
    Intent(requireContext(), activity).also {
        it.putExtra("typeDelivery", typeDelivery)
        it.putExtra("address", address)
        it.putExtra("sum", sum)
        startActivity(it)
    }
}

fun<A : Activity> Fragment.startNewActivityWithId(activity: Class<A>, id: Int){
    Intent(requireContext(), activity).also {
        it.putExtra("id", id)
        startActivity(it)
    }
}
fun<A : Activity> Fragment.startNewActivityWithCartSum(activity: Class<A>, sum: Double){
    Intent(requireContext(), activity).also {
        it.putExtra("sum", sum)
        startActivity(it)
    }
}
fun<A : Activity> Fragment.startNewActivityFromFragment(activity: Class<A>){
    Intent(requireContext(), activity).also {
        startActivity(it)
    }
}


fun View.visible(isVisible : Boolean){
    visibility = if(isVisible) View.VISIBLE else View.GONE
}

