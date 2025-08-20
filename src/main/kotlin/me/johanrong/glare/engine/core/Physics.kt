package me.johanrong.glare.engine.core

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.physics.RigidbodyComponent
import me.johanrong.glare.engine.node.component.physics.collision.BoxColliderComponent
import me.johanrong.glare.engine.node.component.physics.collision.ColliderComponent
import org.joml.Vector3f

class Physics(engine: Engine) {
    private val rigidbodies = mutableListOf<RigidbodyComponent>()
    private val colliders = mutableListOf<ColliderComponent>()

    fun add(node: Node) {
        node.getComponent(Component.RIGIDBODY)?.let { rigidbodies.add(it as RigidbodyComponent) }
        val colliders = node.getComponentsFromCategory(Component.Companion.Category.COLLIDER)
        for (collider in colliders) {
            this.colliders.add(collider as ColliderComponent)
        }
    }

    fun update() {
        return
        // Gravity
        for (rb in rigidbodies) {
            if (!rb.freeze) {
                rb.applyForce(Vector3f(0f, -9.81f, 0f))
            }
        }

        // Integrate motion
        for (rb in rigidbodies) {
            rb.integrate()
        }

        println(colliders)

        // Collision detection and response
        for (i in colliders.indices) {
            for (j in i + 1 until colliders.size) {
                val a = colliders[i] as BoxColliderComponent
                val b = colliders[j] as BoxColliderComponent
                if (a.intersects(b)) {
                    println("Collision detected between ${a.node?.name} and ${b.node?.name}")
                    // Simple collision response: separate objects
                    val rbA = a.node?.getComponent(Component.RIGIDBODY) as RigidbodyComponent
                    val rbB = b.node?.getComponent(Component.RIGIDBODY) as RigidbodyComponent
                    rbA.velocity.negate()
                    rbB.velocity.negate()
                }
            }
        }
    }
}