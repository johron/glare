package me.johanrong.glare.node.component.core

import me.johanrong.glare.node.Node

interface IScript {
    fun init(parent: Node) {}
    fun update(delta: Double) {}
    fun fixedUpdate() {}
    fun render(/*delta: Double*/) {}
    fun cleanup() {}
}