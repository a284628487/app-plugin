package com.ccf.app.util

import android.content.res.AssetManager
import android.content.res.Resources

data class PluginApk(
    val classLoader: ClassLoader,
    val theme: Resources.Theme,
    val assetManager: AssetManager,
    val resources: Resources,
    val packageName: String
)