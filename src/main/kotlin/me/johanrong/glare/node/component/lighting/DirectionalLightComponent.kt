package me.johanrong.glare.node.component.lighting

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.lighting.Light
import org.joml.Vector3f

class DirectionalLightComponent: LightComponent() {
    override val lightType = Light.DIRECTIONAL
    var direction: Vector3f = Vector3f(0f, -1f, 0f)

    override fun applyToShader(shader: ShaderComponent, index: Int) {
        shader.setUniform("uDirLights[$index].direction", direction)
        shader.setUniform("uDirLights[$index].color", color.toVector3f())
        shader.setUniform("uDirLights[$index].intensity", intensity)
    }

    override fun cleanup() {}
}