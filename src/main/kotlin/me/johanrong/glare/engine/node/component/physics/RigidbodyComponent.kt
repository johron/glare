package me.johanrong.glare.engine.node.component.physics

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.ExportProperty
import me.johanrong.glare.engine.node.component.IComponent
import org.joml.Vector3f

open class RigidbodyComponent : IComponent {
    override val type = Component.RIGIDBODY
    override var node: Node? = null

    @ExportProperty var velocity = Vector3f(0f, 0f, 0f)
    @ExportProperty var mass: Float = 1f
    @ExportProperty var force = Vector3f(0f, 0f, 0f)
    @ExportProperty open var freeze = false

    fun applyForce(f: Vector3f) {
        force.add(f)
    }

    fun integrate() {
        if (freeze) return
        val acceleration = Vector3f(force).div(mass)
        velocity.add(Vector3f(acceleration).mul(1/60f))
        node?.transform?.position?.add(Vector3f(velocity).mul(1/60f))
        force.zero()
    }

    override fun onAttach(node: Node) {
        this.node = node

        node.engine.physics.add(node)
    }
}