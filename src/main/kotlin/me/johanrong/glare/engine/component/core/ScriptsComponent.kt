package me.johanrong.glare.engine.component.core

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.scripting.IScript

class ScriptsComponent(
    var scripts: MutableList<IScript> = mutableListOf(),
) : IComponent {
    override val type = Component.SCRIPTS
    override var node: Node? = null

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
        scripts.forEach { it.onAttach(node) }
        scripts.forEach { it.init() }
    }

    override fun cleanup() {
        scripts.forEach { it.cleanup() }
        scripts.clear()
    }
}