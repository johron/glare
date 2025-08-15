package me.johanrong.glare.engine.node.component.core

import me.johanrong.glare.engine.node.Node

interface IScript {
    fun init(parent: Node) {}
    fun update(delta: Double) {}
    fun fixedUpdate() {}
    fun render(/*delta: Double*/) {}
    fun cleanup() {}
}