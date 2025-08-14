package me.johanrong.glare.node.component.physics

import me.johanrong.glare.common.Force
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import org.joml.Vector3f

class RigidbodyComponent : IComponent {
    override val type = Component.RIGIDBODY
    override var node: Node? = null

    private var velocity = Force(0.0f)
    private var angularVelocity = Force(0.0f)

    var mass: Float = 1f
    var inertia = 0f
    var centerMass = Vector3f(0.0f)

    var constantForce = Force(0.0f, -0.0001f, 0.0f)
    var constantTorque = Force(0.0f)

    var freeze: Boolean = false

    fun applyForce(force: Force) {
        velocity.add(force)
    }

    fun applyTorque(torque: Force) {
        angularVelocity.add(torque)
    }

    fun getVelocity(): Force {
        return velocity
    }

    fun getAngularVelocity(): Force {
        return angularVelocity
    }

    fun setVelocity(velocity: Force) {
        this.velocity = velocity
    }

    fun setAngularVelocity(angularVelocity: Force) {
        this.angularVelocity = angularVelocity
    }

    override fun onAttach(node: Node) {
        this.node = node

        node.engine.physics.add(node)
    }
}