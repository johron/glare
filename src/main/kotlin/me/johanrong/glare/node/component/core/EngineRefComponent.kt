package me.johanrong.glare.node.component.core

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component

class EngineRefComponent(private val engine: Engine) : IComponent {
    override val type = Component.ENGINE_REF

    fun getEngine(): Engine {
        return engine
    }

    override fun cleanup() {}
}