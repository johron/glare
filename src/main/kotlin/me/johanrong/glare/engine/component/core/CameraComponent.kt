package me.johanrong.glare.engine.component.core

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node

class CameraComponent : IComponent {
    override val type = Component.CAMERA
    override var node: Node? = null
    
    override fun cleanup() {}
}