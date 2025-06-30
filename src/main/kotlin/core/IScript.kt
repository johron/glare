package me.johanrong.glare.core

interface IScript {
    fun init(engine: GlareEngine? = null)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}