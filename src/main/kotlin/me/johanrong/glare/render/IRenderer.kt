package me.johanrong.glare.render

import me.johanrong.glare.node.Node

interface IRenderer {
    val priority: Int

    fun init()
    fun render(node: Node)
    fun cleanup()
}