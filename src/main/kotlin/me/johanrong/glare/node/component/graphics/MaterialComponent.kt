package me.johanrong.glare.node.component.graphics

import me.johanrong.glare.math.Color
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent

class MaterialComponent : IComponent {
    override val type = Component.MATERIAL

    // Material properties
    var diffuse: Color = Color(1f)  // Diffuse color (base color of the material)
    var specular: Color = Color(0.5f)  // Specular color (highlight color)
    var shininess: Float = 32f  // Shininess factor (controls specular highlight size)

    fun applyToShader(shader: ShaderComponent) {
        // Set material properties in the shader
        shader.setUniform("material.diffuse", diffuse.toVector3f())
        shader.setUniform("material.specular", specular.toVector3f())
        shader.setUniform("material.shininess", shininess)
    }

    override fun cleanup() {}
}