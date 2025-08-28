package me.johanrong.glare.engine.scripting

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.io.Input
import me.johanrong.glare.engine.type.Transform

abstract class Script : IScript {
    lateinit var node: Node
    val engine: Engine get() = node.engine
    val input: Input get() = engine.input
    val transform: Transform get() = node.transform

    override fun onAttach(node: Node) {
        this.node = node
    }
}