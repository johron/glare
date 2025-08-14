package me.johanrong.glare.node.component.lighting

import me.johanrong.glare.common.Color
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.node.component.graphics.ShaderComponent

abstract class LightComponent(var intensity: Float = 1.0f, var color: Color = Color(1f)) : IComponent {
    override val type = Component.LIGHT
    abstract val lightType: Light
    override var node: Node? = null

    abstract fun applyToShader(shader: ShaderComponent, index: Int)
}