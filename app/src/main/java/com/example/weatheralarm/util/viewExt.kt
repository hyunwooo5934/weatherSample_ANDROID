package com.example.weatheralarm.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object viewExt {

    @JvmStatic
    @BindingAdapter("visible")
    fun visible(view: View, value: Boolean) {
        view.visibility = when (value) {
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    }

}