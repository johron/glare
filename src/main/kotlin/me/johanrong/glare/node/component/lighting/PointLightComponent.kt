package me.johanrong.glare.node.component.lighting

import me.johanrong.glare.common.Color
import me.johanrong.glare.node.component.graphics.ShaderComponent

class PointLightComponent(var constant: Float = 0.01f, var linear: Float = 0.02f, var quadratic: Float = 0.002f, intensity: Float = 23.0f, color: Color = Color(0.988f,0.729f,0.012f)): LightComponent(intensity, color) {
    override val lightType = Light.POINT

    override fun applyToShader(shader: ShaderComponent, index: Int) {
        shader.setUniform("uPointLights[$index].position", node!!.transform.getPosition())
        shader.setUniform("uPointLights[$index].color", color.toVector3f())
        shader.setUniform("uPointLights[$index].intensity", intensity)
        shader.setUniform("uPointLights[$index].constant", constant)
        shader.setUniform("uPointLights[$index].linear", linear)
        shader.setUniform("uPointLights[$index].quadratic", quadratic)
    }

    override fun cleanup() {}
}