package com.ccf.pluginlib

interface IGame {

    fun begin(callback: IGameCallback)

    fun end()

    fun setName(name: String)

    fun getName(): String


}