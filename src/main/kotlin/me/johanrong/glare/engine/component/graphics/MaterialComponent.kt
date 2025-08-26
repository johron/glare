package me.johanrong.glare.engine.component.graphics

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.type.Color

class MaterialComponent : IComponent {
    override val type = Component.MATERIAL
    override var node: Node? = null
    
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