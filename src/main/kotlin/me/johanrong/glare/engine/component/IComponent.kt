package me.johanrong.glare.engine.component

import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.util.log

interface IComponent {
    val type: Component
    var node: Node?

    val dependencies: List<Component> get() = emptyList()

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