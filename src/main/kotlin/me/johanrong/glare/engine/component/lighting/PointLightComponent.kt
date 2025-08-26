package me.johanrong.glare.engine.component.lighting

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.graphics.ShaderComponent
import me.johanrong.glare.engine.type.Color

class PointLightComponent(var constant: Float = 0.001f, var linear: Float = 0.002f, var quadratic: Float = 0.0005f, intensity: Float = 10.0f, color: Color = Color(1f)): LightComponent(intensity, color) {
    override val type = Component.POINT_LIGHT

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