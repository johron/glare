package me.johanrong.glare.engine.core

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.physics.RigidbodyComponent
import org.joml.Vector3f

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
                val acceleration = rigidbody.constantForce.div(rigidbody.mass)
                acceleration.add(Vector3f(0f, -9.81f, 0f)) // Gravity
                rigidbody.setVelocity(rigidbody.getVelocity().add(acceleration))

                val dragCoefficient = 0.05f // Can be adjusted based on object properties
                val velocityMagnitude = rigidbody.getVelocity().length()
                val dragDirection = Vector3f(rigidbody.getVelocity()).normalize().negate()
                val dragForce = dragDirection.mul(dragCoefficient * velocityMagnitude * velocityMagnitude)
                rigidbody.applyForce(dragForce)

                // Update position and rotation based on velocity and angular velocity
                node.transform.position.add(rigidbody.getVelocity() / 60f)
                node.transform.rotation.rotateXYZ(
                    rigidbody.getAngularVelocity().x / 60.0f,
                    rigidbody.getAngularVelocity().y / 60.0f,
                    rigidbody.getAngularVelocity().z / 60.0f
                )

                println("Node: ${node.name}, Position: ${node.transform.position}, Velocity: ${rigidbody.getVelocity()}, Acceleration: ${rigidbody.constantForce.div(rigidbody.mass)}")
            }
        }
    }
}