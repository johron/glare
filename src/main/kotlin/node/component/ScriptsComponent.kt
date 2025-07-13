package me.johanrong.glare.node.component

import me.johanrong.glare.type.IScript
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
}