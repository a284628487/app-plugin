package com.ccf.pluginlib

interface IGameCallback {

    fun onGameEnded(game: IGame)

    fun onGamePaused(game: IGame)
}