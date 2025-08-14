package me.johanrong.glare.node.component.physics

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import org.joml.Vector3f

class RigidbodyComponent : IComponent {
    override val type = Component.RIGIDBODY
    override var node: Node? = null

    private var velocity: Vector3f = Vector3f(0.0f)
    private var angularVelocity: Vector3f = Vector3f(0.0f)

    var mass: Double = 1.0
    var centerMass = Vector3f(0.0f)

    var constantForce: Vector3f = Vector3f(0.0f)
    var constantTorque: Vector3f = Vector3f(0.0f)

    var inertia: Vector3f = Vector3f(0.0f)
    var freeze: Boolean = false

    fun applyForce(force: Vector3f) {
        velocity.add(force)
    }

    fun applyTorque(torque: Vector3f) {
        angularVelocity.add(torque)
    }
}