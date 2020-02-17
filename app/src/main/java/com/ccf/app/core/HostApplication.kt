package com.ccf.app.core

import android.app.Application
import android.content.Context
import com.ccf.app.util.FileUtils
import com.ccf.app.util.HookUtils

class HostApplication: Application() {

    val TAG = "HostApplication"

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        FileUtils.copyApksToDataFiles(base!!)
        //
        HookUtils.hookActivityThreadInstrumentation()
        HookUtils.hookActivityManager()
    }
}