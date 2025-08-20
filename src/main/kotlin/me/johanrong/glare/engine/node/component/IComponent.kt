package me.johanrong.glare.engine.node.component

import me.johanrong.glare.engine.node.Node

interface IComponent {
    val type: Component
    var node: Node?
    var enabled: Boolean

    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }

    fun onAttach(node: Node) {
        this.node = node
    }

    fun cleanup() {}
}