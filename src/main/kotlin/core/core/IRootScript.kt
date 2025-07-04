package me.johanrong.glare.core.core

import me.johanrong.glare.core.GlareEngine

interface IRootScript {
    fun init(engine: GlareEngine)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}