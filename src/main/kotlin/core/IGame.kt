package me.johanrong.glare.core

interface IGame {
    fun init(engine: GlareEngine)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}