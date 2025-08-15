package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.node.Node

interface IRenderer {
    val priority: Int

    fun init()
    fun render(node: Node)
    fun cleanup()
}