package com.example.onlineshoppoizon.utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.yandex.mapkit.mapview.MapView


class AppMapView(context: Context?, attrs: AttributeSet?) :
    MapView(context, attrs) {
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_UP -> {
                println("unlocked")
                this.parent.requestDisallowInterceptTouchEvent(false)
            }

            MotionEvent.ACTION_DOWN -> {
                println("locked")
                this.parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}