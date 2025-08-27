package me.johanrong.glare.engine.component.physics

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.scripting.Exported
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node
import org.joml.Vector3f

open class RigidbodyComponent : IComponent {
    override val type = Component.RIGIDBODY
    override var node: Node? = null

    @Exported("Velocity") var velocity = Vector3f(0f, 0f, 0f)
    @Exported("Mass") var mass: Float = 1f
    @Exported("Force") var force = Vector3f(0f, 0f, 0f)
    @Exported("Freeze", false) open var freeze = false

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