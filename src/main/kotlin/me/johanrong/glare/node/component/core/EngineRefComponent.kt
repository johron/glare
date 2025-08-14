package me.johanrong.glare.node.component.core

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent

class EngineRefComponent(private val engine: Engine) : IComponent {
    override val type = Component.ENGINE_REF
    override var node: Node? = null

    fun getEngine(): Engine {
        return engine
    }

    override fun cleanup() {}
}