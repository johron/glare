package me.johanrong.glare.core

interface INodeScript {
    fun init()
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}