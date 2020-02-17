package com.ccf.app.util

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipFile

object FileUtils {

    private const val TAG = "FileUtils"

    const val apk1Name = "plugin-game1.apk"
    const val apk2Name = "plugin-game2.apk"

    private val packageNameMap = mutableMapOf(
        Pair(apk1Name, "com.ccf.game1"),
        Pair(apk2Name, "com.ccf.game2")
    )

    private val classLoaders = mutableMapOf<String, PluginApk>()

    fun copyApksToDataFiles(context: Context) {
        copyApkToDataFiles(context, apk1Name)
        copyApkToDataFiles(context, apk2Name)
    }

    private fun copyApkToDataFiles(context: Context, apkName: String) {
        val inputStream = context.assets.open(apkName)
        var byteArray = ByteArray(2000)
        var len = inputStream.read(byteArray)
        val outputStream = FileOutputStream(File(context.filesDir, apkName))
        while (len > 0) {
            outputStream.write(byteArray, 0, len)
            len = inputStream.read(byteArray)
        }
        inputStream.close()
        outputStream.close()
    }

    fun getApkPlugin(context: Context, pluginName: String): PluginApk {
        var loader = classLoaders[pluginName]

        loader?.let {
            return it
        } ?: run {
            val apkFile = context.getFileStreamPath(pluginName)
            Log.e(TAG, "apkFile = ${apkFile}")

            val file = context.getDir("dex", 0)
            Log.e(TAG, "dexPath = ${file}")

            val newClassLoader = DexClassLoader(
                apkFile.path, file.absolutePath, null,
                Thread.currentThread().contextClassLoader
            )
            val pluginApk = loadResources(context, pluginName, newClassLoader)

            classLoaders[pluginName] = pluginApk

            return pluginApk
        }
    }

    private fun loadResources(
        context: Context,
        pluginName: String,
        classLoader: ClassLoader
    ): PluginApk {
        val apkFile = context.getFileStreamPath(pluginName)

        val clazz = Class.forName("android.content.res.AssetManager")

        val assetManager = clazz.newInstance() as AssetManager
        val addAssetPathMethod = clazz.getMethod("addAssetPath", Class.forName("java.lang.String"))
        addAssetPathMethod.isAccessible = true
        addAssetPathMethod.invoke(assetManager, apkFile.path)

        val resources = Resources(
            assetManager,
            context.resources.displayMetrics,
            context.resources.configuration
        )

        val theme = resources.newTheme()
        theme.setTo(context.theme)

        return PluginApk(classLoader, theme, assetManager, resources, packageNameMap[pluginName]!!)
    }

    fun zipFile(context: Context) {
        val apkFile = context.getFileStreamPath(apk1Name)
        Log.e(TAG, "${apkFile.absolutePath}")

        val zipFile = ZipFile(apkFile.path)
        val zipEntry = zipFile.getEntry("classes2.dex")
        Log.e(TAG, "$zipEntry")
    }
}