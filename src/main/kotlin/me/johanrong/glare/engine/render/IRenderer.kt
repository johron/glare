package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.core.Node

interface IRenderer {
    fun init()
    fun render(node: Node)
    fun cleanup()
}