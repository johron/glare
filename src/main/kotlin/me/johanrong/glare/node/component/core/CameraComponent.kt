package me.johanrong.glare.node.component.core

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent

class CameraComponent : IComponent {
    override val type = Component.CAMERA
    override var node: Node? = null

    override fun cleanup() {}
}