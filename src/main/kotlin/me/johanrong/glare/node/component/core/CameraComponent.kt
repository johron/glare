package me.johanrong.glare.node.component.core

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.node.component.Component

class CameraComponent : IComponent {
    override val type = Component.CAMERA

    override fun cleanup() {}
}