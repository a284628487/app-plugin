package com.ccf.app

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView

class RegisteredActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this).apply {
            text = "RegisteredActivity"
            gravity = Gravity.CENTER
        }
        setContentView(textView)
    }
}