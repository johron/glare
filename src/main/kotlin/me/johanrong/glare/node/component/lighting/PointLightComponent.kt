package me.johanrong.glare.node.component.lighting

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.lighting.Light
import org.joml.Vector3f

class PointLightComponent: LightComponent() {
    override val lightType = Light.POINT
    var attenuation: Vector3f = Vector3f(1f, 0.09f, 0.032f) // constant, linear, quadratic

    override fun applyToShader(shader: ShaderComponent, index: Int) {
        shader.setUniform("uPointLights[$index].position", node.transform.getPosition())
        shader.setUniform("uPointLights[$index].color", color.toVector3f())
        shader.setUniform("uPointLights[$index].intensity", intensity)
        shader.setUniform("uPointLights[$index].constant", attenuation.x)
        shader.setUniform("uPointLights[$index].linear", attenuation.y)
        shader.setUniform("uPointLights[$index].quadratic", attenuation.z)
    }

    override fun cleanup() {}
}