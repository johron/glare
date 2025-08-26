package me.johanrong.glare.engine.scripting

import me.johanrong.glare.engine.core.Node

interface IScript {
    fun onAttach(node: Node) {}

    fun init() {}
    fun update(delta: Double) {}
    fun fixedUpdate() {}
    fun render(/*delta: Double*/) {}
    fun cleanup() {}
}