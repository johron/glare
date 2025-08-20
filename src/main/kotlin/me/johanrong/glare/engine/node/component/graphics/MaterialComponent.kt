package me.johanrong.glare.engine.node.component.graphics

import me.johanrong.glare.engine.common.Color
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent

class MaterialComponent : IComponent {
    override val type = Component.MATERIAL
    override var node: Node? = null
    override var enabled: Boolean = true

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