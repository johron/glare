package me.johanrong.glare.core

import me.johanrong.glare.node.Node

interface IScript {
    fun init(parent: Node)
    fun update(delta: Double)
    fun render(/*delta: Double*/)
    fun cleanup()
}