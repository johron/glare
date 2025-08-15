package me.johanrong.glare.engine.core

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.physics.RigidbodyComponent

class Physics(engine: Engine) {
    private var nodes = mutableListOf<Node>()

    fun add(node: Node) {
        nodes.add(node)
    }

    fun update() {
        for (node in nodes) {
            val rigidbody = node.getComponent(Component.RIGIDBODY) as RigidbodyComponent?
            if (rigidbody != null && !rigidbody.freeze) {
                // Apply constant force and torque
                rigidbody.applyForce(rigidbody.constantForce)
                rigidbody.applyTorque(rigidbody.constantTorque)

                // Update position and rotation based on velocity and angular velocity
                node.transform.position.add(rigidbody.getVelocity() / 60.0f)
                node.transform.rotation.rotateXYZ(
                    rigidbody.getAngularVelocity().x / 60.0f,
                    rigidbody.getAngularVelocity().y / 60.0f,
                    rigidbody.getAngularVelocity().z / 60.0f
                )
            }
        }
    }
}