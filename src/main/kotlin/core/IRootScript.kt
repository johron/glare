package me.johanrong.glare.core

import me.johanrong.glare.node.Node

interface IRootScript {
    fun init(engine: GlareEngine)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}