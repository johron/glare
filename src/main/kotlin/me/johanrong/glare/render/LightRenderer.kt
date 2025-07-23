package me.johanrong.glare.render

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.lighting.LightComponent

class LightRenderer(val engine: Engine) : IRenderer {
    override val rpriority: Int = 1
    private var lights = mutableListOf<LightComponent>()

    override fun init() {
        lights.clear()
    }

    override fun render(node: Node) {
        if (!node.hasComponent(Component.LIGHT)) return

        val lightComponent = node.getComponent(Component.LIGHT) as LightComponent

        if (engine.getRenderer().currentShader != null) {
            val shader = engine.getRenderer().currentShader!!
            lights.add(lightComponent)

            lightComponent.applyToShader(shader, lights.size)
        }
    }

    override fun cleanup() {}
}