package com.ccf.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(this.localClassName, "onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.w(this.localClassName, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.w(this.localClassName, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(this.localClassName, "onDestroy")
    }
}