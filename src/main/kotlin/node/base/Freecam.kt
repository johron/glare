package me.johanrong.glare.node.base

import me.johanrong.glare.TestGame.Companion.engine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.Node
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.type.io.MouseButton
import me.johanrong.glare.util.Input
import org.joml.Math
import org.joml.Vector3d

class Freecam (name: String, parent: Node, transform: Transform) : Camera(name, FreecamScript(), parent, transform) {
    constructor (parent: Node) : this("Freecam", parent, Transform())
    constructor (parent: Node, transform: Transform) : this("Freecam", parent, transform)
}

class FreecamScript : IScript {
    lateinit var node: Camera

    var input = Input(engine)

    override fun init(parent: Node) {
        this.node = parent as Camera
    }

    override fun update(delta: Double) {
        if (input.isKeyHeld(Keycode.W)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val forward = Vector3d(
                Math.sin(yawRad),
                0.0,
                Math.cos(yawRad)
            )
            forward.normalize().mul(delta * 5)
            node.transform.position.add(forward)
        }
        if (input.isKeyHeld(Keycode.S)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val backward = Vector3d(
                -Math.sin(yawRad),
                0.0,
                -Math.cos(yawRad)
            )
            backward.normalize().mul(delta * 5)
            node.transform.position.add(backward)
        }
        if (input.isKeyHeld(Keycode.A)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val left = Vector3d(
                -Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            left.normalize().mul(delta * 5)
            node.transform.position.add(left)
        }
        if (input.isKeyHeld(Keycode.D)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val right = Vector3d(
                Math.cos(yawRad),
                0.0,
                -Math.sin(yawRad)
            )
            right.normalize().mul(delta * 5)
            node.transform.position.add(right)
        }

        if (input.isKeyHeld(Keycode.SPACE)) {
            node.transform.translate(0.0, 5.0 * delta, 0.0)
        }
        if (input.isKeyHeld(Keycode.SHIFT)) {
            node.transform.translate(0.0, -5.0 * delta, 0.0)
        }

        val deltaMouse = input.getMouseDelta()
        if (input.isMouseButtonPressed(MouseButton.RIGHT)) {
            node.transform.rotation.addYaw(deltaMouse.x * 0.1)
            node.transform.rotation.addPitch(deltaMouse.y * 0.1)
        }
    }

    override fun render() {}
    override fun cleanup() {}
}