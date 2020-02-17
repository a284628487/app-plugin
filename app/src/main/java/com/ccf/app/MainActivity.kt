package com.ccf.app

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.ccf.app.util.FileUtils
import com.ccf.pluginlib.IGame
import com.ccf.pluginlib.IGameCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load_apk1_name.setOnClickListener {
            load_apk1_name.text = "apk1Name = ${loadApkName(FileUtils.apk1Name)}"
        }
        load_apk2_name.setOnClickListener {
            load_apk2_name.text = "apk2Name = ${loadApkName(FileUtils.apk2Name)}"
        }
        load_apk1_icon.setOnClickListener {
            load_apk1_icon.setImageDrawable(getApkDrawable(FileUtils.apk1Name))
        }
        load_apk2_icon.setOnClickListener {
            load_apk2_icon.setImageDrawable(getApkDrawable(FileUtils.apk2Name))
        }

        load_apk1_class.setOnClickListener {
            loadApk1GameClass()
        }

        start_hook_activity.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisteredActivity::class.java))
        }
    }

    private fun loadApk1GameClass() {
        val apk1ClassLoader = FileUtils.getApkPlugin(this, FileUtils.apk1Name).classLoader

        val clazz = apk1ClassLoader.loadClass("com.ccf.game1.entity.BasketballGame")
        Log.e(TAG, "GameClass = $clazz")

        var game = clazz.newInstance() as IGame
        game.begin(object : IGameCallback {
            override fun onGameEnded(game: IGame) {
                Log.e(TAG, "onGameEnded: ${game.getName()}")
                load_apk1_class.text = "onGameEnded = ${game.getName()}"
            }

            override fun onGamePaused(game: IGame) {
                Log.e(TAG, "onGamePaused: ${game.getName()}")
            }
        })
        game.setName("HostApp-PluginGame")
        game.end()
    }

    private fun loadApkName(apkName: String): String {
        val apkPlugin = FileUtils.getApkPlugin(this, apkName)
        val resources = apkPlugin.resources

        val id = resources.getIdentifier("app_name", "string", apkPlugin.packageName)

        return resources.getString(id)
    }

    private fun getApkDrawable(apkName: String): Drawable {
        val apkPlugin = FileUtils.getApkPlugin(this, apkName)
        val resources = apkPlugin.resources

        val id = resources.getIdentifier("interesting", "drawable", apkPlugin.packageName)

        return resources.getDrawable(id, apkPlugin.theme)
    }

}
