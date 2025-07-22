package me.johanrong.glare.node.component.core

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.node.component.ComponentType

class CameraComponent : IComponent {
    override val type = ComponentType.CAMERA

    override fun cleanup() {}
}