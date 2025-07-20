import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.core.IScript
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.type.io.MouseButton
import me.johanrong.glare.util.Input
import org.joml.Math
import org.joml.Vector3d

class FreecamScript(var speed: Double = 5.0) : IScript {
    lateinit var node: Node
    val engine = TestGame.engine

    val input = Input(engine)

    override fun init(parent: Node) {
        this.node = parent
    }

    override fun update(delta: Double) {
        if (input.isKeyHeld(Keycode.W)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val forward = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            forward.normalize().mul(delta * speed)
            node.transform.position.add(forward)
        }
        if (input.isKeyHeld(Keycode.S)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val backward = Vector3d(
                -Math.cos(yawRad),
                0.0,
                -Math.sin(yawRad)
            )
            backward.normalize().mul(delta * speed)
            node.transform.position.add(backward)
        }
        if (input.isKeyHeld(Keycode.A)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw() - 90)
            val left = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            left.normalize().mul(delta * speed)
            node.transform.position.add(left)
        }
        if (input.isKeyHeld(Keycode.D)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw() + 90)
            val right = Vector3d(
                Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            right.normalize().mul(delta * speed)
            node.transform.position.add(right)
        }

        if (input.isKeyHeld(Keycode.SPACE)) {
            node.transform.translate(0.0, speed * delta, 0.0)
        }
        if (input.isKeyHeld(Keycode.SHIFT)) {
            node.transform.translate(0.0, -speed * delta, 0.0)
        }

        val deltaMouse = input.getMouseDelta()
        if (input.isMouseButtonPressed(MouseButton.RIGHT)) {
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