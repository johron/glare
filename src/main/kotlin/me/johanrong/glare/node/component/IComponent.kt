package me.johanrong.glare.node.component

import me.johanrong.glare.node.Node

interface IComponent {
    val type: Component
    var node: Node?

    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }

    fun onAttach(node: Node) {
        this.node = node
    }

    fun cleanup() {}
}