package me.johanrong.glare.engine.node.component

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.util.log

interface IComponent {
    val type: Component
    var node: Node?

    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }

    fun onAttach(node: Node) {
        this.node = node
    }

    fun apply() {
        log("${getComponentName()} is not applicable for this function")
    }

    fun cleanup() {}
}