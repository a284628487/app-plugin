package com.ccf.app

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView

class HookedActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "HookedActivity"
            gravity = Gravity.CENTER
        }
        setContentView(textView)
    }
}