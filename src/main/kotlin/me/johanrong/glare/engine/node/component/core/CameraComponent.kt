package me.johanrong.glare.engine.node.component.core

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent

class CameraComponent : IComponent {
    override val type = Component.CAMERA
    override var node: Node? = null

    override fun cleanup() {}
}