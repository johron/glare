package test

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.io.Keycode
import me.johanrong.glare.engine.io.MouseButton
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.scripting.IScript
import me.johanrong.glare.engine.scripting.Script
import org.joml.Math
import org.joml.Vector3d

class FreecamScript(var speed: Double = 5.0) : Script() {
    override fun update(delta: Double) {
        if (engine.input.isKeyHeld(Keycode.W)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val forward = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            forward.normalize().mul(delta * speed)
            node.transform.position.add(forward)
        }
        if (engine.input.isKeyHeld(Keycode.S)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val backward = Vector3d(
                -Math.cos(yawRad),
                0.0,
                -Math.sin(yawRad)
            )
            backward.normalize().mul(delta * speed)
            node.transform.position.add(backward)
        }
        if (engine.input.isKeyHeld(Keycode.A)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw() - 90)
            val left = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            left.normalize().mul(delta * speed)
            node.transform.position.add(left)
        }
        if (engine.input.isKeyHeld(Keycode.D)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw() + 90)
            val right = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            right.normalize().mul(delta * speed)
            node.transform.position.add(right)
        }

        if (engine.input.isKeyHeld(Keycode.SPACE)) {
            node.transform.translate(0.0, speed * delta, 0.0)
        }
        if (engine.input.isKeyHeld(Keycode.SHIFT)) {
            node.transform.translate(0.0, -speed * delta, 0.0)
        }

        val deltaMouse = engine.input.getMouseDelta()
        if (engine.input.isMouseButtonPressed(MouseButton.RIGHT)) {
            node.transform.rotation.addYaw(deltaMouse.x * 0.1)

            // Clamp pitch to prevent flipping
            val newPitch = node.transform.rotation.getPitch() - deltaMouse.y * 0.1
            if (newPitch in -89.0..89.0) {
                node.transform.rotation.setPitch(newPitch)
            } else if (newPitch < -89.0) {
                node.transform.rotation.setPitch(-89.0)
            } else {
                node.transform.rotation.setPitch(89.0)
            }
        }
    }

    override fun render() {}
    override fun cleanup() {}
}