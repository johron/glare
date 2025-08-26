package me.johanrong.glare.engine.component.lighting

import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.component.graphics.ShaderComponent
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.type.Color

abstract class LightComponent(var intensity: Float = 1.0f, var color: Color = Color(1f)) : IComponent {
    override var node: Node? = null
    
    abstract fun applyToShader(shader: ShaderComponent, index: Int)
}