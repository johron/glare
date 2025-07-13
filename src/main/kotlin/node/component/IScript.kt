package me.johanrong.glare.node.component

import me.johanrong.glare.node.Node

interface IScript {
    fun init(parent: Node)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}