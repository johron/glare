package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.component.CCategory
import me.johanrong.glare.engine.component.lighting.LightComponent
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.Node

class LightRenderer(val engine: Engine) : IRenderer {
    private var lights = mutableListOf<LightComponent>()

    override fun init() {
        lights.clear()
    }

    override fun render(node: Node) {
        val shader = engine.getRenderer().currentShader

        if (node.hasComponentsFromCategory(CCategory.LIGHT)) {
            val lightComponents = node.getComponentsFromCategory(CCategory.LIGHT)

            for (component in lightComponents) {
                lights.add(component as LightComponent)
            }
        }

        if (shader != null) {
            shader.setUniform("uNumPointLights", lights.size)
            for (light in lights) {
                light.applyToShader(shader, lights.indexOf(light))
            }
        }
    }

    override fun cleanup() {}
}