package me.johanrong.glare.engine.node.component.physics.collision

import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.IComponent

abstract class ColliderComponent(var transform: Transform) : IComponent {
    override var node: Node? = null
    override var enabled: Boolean = true
}