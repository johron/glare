package me.johanrong.glare.node.component.core

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component

class ScriptsComponent(var scripts: MutableList<IScript> = mutableListOf()) : IComponent {
    override val type = Component.SCRIPTS

    fun addScript(script: IScript) {
        scripts.add(script)
    }

    fun removeScript(script: IScript) {
        scripts.remove(script)
    }

    fun clear() {
        scripts.clear()
    }

    override fun cleanup() {
        scripts.forEach { it.cleanup() }
        scripts.clear()
    }
}