package me.johanrong.glare.core

interface IRootScript {
    fun init(engine: GlareEngine)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}