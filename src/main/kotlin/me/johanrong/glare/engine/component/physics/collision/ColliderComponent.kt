package me.johanrong.glare.engine.component.physics.collision

import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.type.Transform

abstract class ColliderComponent(var transform: Transform) : IComponent {
    override var node: Node? = null
    }