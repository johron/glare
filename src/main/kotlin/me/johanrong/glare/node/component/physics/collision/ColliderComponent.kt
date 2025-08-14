package me.johanrong.glare.node.component.physics.collision

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent

abstract class ColliderComponent : IComponent {
    override val type = Component.COLLIDER
    override var node: Node? = null
    
    abstract val colliderType: Collider
    abstract var contacts: MutableList<ColliderComponent>

    abstract fun colliding(other: ColliderComponent): Boolean
}