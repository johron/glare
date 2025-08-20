package me.johanrong.glare.engine.node.component.core

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent

class ScriptsComponent(var scripts: MutableList<IScript> = mutableListOf()) : IComponent {
    override val type = Component.SCRIPTS
    override var node: Node? = null
    override var enabled: Boolean = true

    fun addScript(script: IScript) {
        scripts.add(script)
    }

    fun removeScript(script: IScript) {
        scripts.remove(script)
    }

    fun clear() {
        scripts.clear()
    }

    override fun onAttach(node: Node) {
        this.node = node
        scripts.forEach { it.init(node) }
    }

    override fun cleanup() {
        scripts.forEach { it.cleanup() }
        scripts.clear()
    }
}