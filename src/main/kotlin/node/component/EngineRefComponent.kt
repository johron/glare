package me.johanrong.glare.node.component

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.type.node.Component

class EngineRefComponent(private val engine: GlareEngine) : IComponent {
    override val type = Component.ENGINE_REF

    fun getEngine(): GlareEngine {
        return engine
    }
}