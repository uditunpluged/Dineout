package com.phantasmist.dineout.Utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.phantasmist.dineout.R

class Utils {
    companion object {


        @BindingAdapter("android:src")
        @JvmStatic
        fun setImageUrl(view: ImageView, url: String) {
            GlideApp.with(view.context).load(url).placeholder(R.drawable.placeholder).into(view)
        }
    }
}