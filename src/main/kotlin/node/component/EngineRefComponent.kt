package me.johanrong.glare.node.component

import me.johanrong.glare.core.GlareEngine

class EngineRefComponent(private val engine: GlareEngine) : IComponent {
    fun getEngine(): GlareEngine {
        return engine
    }
}