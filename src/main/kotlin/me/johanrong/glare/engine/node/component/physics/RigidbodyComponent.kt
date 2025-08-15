package me.johanrong.glare.engine.node.component.physics

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent
import org.joml.Vector3f

class RigidbodyComponent : IComponent {
    override val type = Component.RIGIDBODY
    override var node: Node? = null

    private var velocity = Vector3f(0.0f)
    private var angularVelocity = Vector3f(0.0f)

    var mass: Float = 1f
    var inertia = 0f
    var centerMass = Vector3f(0.0f)

    var constantForce = Vector3f(0.0f, -1f, 0.0f)
    var constantTorque = Vector3f(0.0f)

    var freeze: Boolean = false

    fun applyForce(force: Vector3f) {
        velocity.add(force)
    }

    fun applyTorque(torque: Vector3f) {
        angularVelocity.add(torque)
    }

    fun getVelocity(): Vector3f {
        return velocity
    }

    fun getAngularVelocity(): Vector3f {
        return angularVelocity
    }

    fun setVelocity(velocity: Vector3f) {
        this.velocity = velocity
    }

    fun setAngularVelocity(angularVelocity: Vector3f) {
        this.angularVelocity = angularVelocity
    }

    override fun onAttach(node: Node) {
        this.node = node

        node.engine.physics.add(node)
    }
}