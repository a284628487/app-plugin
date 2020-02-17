package com.ccf.game1.entity

import com.ccf.pluginlib.IGame
import com.ccf.pluginlib.IGameCallback

class BasketballGame : IGame {

    private var gameName = ""
    private var callback: IGameCallback? = null

    override fun setName(name: String) {
        gameName = name
    }

    override fun getName(): String {
        return gameName
    }

    override fun begin(callback: IGameCallback) {
        this.callback = callback
    }

    override fun end() {
        callback?.onGameEnded(this)
    }

}