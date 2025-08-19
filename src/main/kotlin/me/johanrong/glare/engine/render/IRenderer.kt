package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.node.Node

interface IRenderer {
    fun init()
    fun render(node: Node)
    fun cleanup()
}