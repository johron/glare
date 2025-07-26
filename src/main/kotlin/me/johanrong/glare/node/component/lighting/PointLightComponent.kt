package me.johanrong.glare.node.component.lighting

import me.johanrong.glare.math.Color
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.lighting.Light
import org.joml.Vector3f

class PointLightComponent(var constant: Float = 1f, var linear: Float = 0.09f, var quadratic: Float = 0.032f, intensity: Float = 1.0f, color: Color = Color(1.0f)): LightComponent(intensity, color) {
    override val lightType = Light.POINT

    override fun applyToShader(shader: ShaderComponent, index: Int) {
        shader.setUniform("uPointLights[$index].position", node.transform.getPosition())
        shader.setUniform("uPointLights[$index].color", color.toVector3f())
        shader.setUniform("uPointLights[$index].intensity", intensity)
        shader.setUniform("uPointLights[$index].constant", constant)
        shader.setUniform("uPointLights[$index].linear", linear)
        shader.setUniform("uPointLights[$index].quadratic", quadratic)
    }

    override fun cleanup() {}
}