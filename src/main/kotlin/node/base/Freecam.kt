package me.johanrong.glare.node.base

import me.johanrong.glare.TestGame.Companion.engine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.mesh.ShaderComponent
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.type.io.MouseButton
import me.johanrong.glare.util.Defaults
import me.johanrong.glare.util.Input
import me.johanrong.glare.util.Loader
import org.joml.Math
import org.joml.Vector3d

class Freecam (name: String, parent: Node, transform: Transform, speed: Double?) : Camera(name, FreecamScript(), parent, transform) {
    constructor (parent: Node) : this("Freecam", parent, Transform(), null)
    constructor (parent: Node, transform: Transform) : this("Freecam", parent, transform, null)
}

class FreecamScript(var speed: Double = Defaults.FREECAM_SPEED) : IScript {
    lateinit var node: Camera

    override fun init(parent: Node) {
        this.node = parent as Camera
    }

    override fun update(delta: Double) {
        if (Input.isKeyHeld(Keycode.W)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val forward = Vector3d(
                Math.sin(yawRad),
                0.0,
                Math.cos(yawRad)
            )
            forward.normalize().mul(delta * speed)
            node.transform.position.add(forward)
        }
        if (Input.isKeyHeld(Keycode.S)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val backward = Vector3d(
                -Math.sin(yawRad),
                0.0,
                -Math.cos(yawRad)
            )
            backward.normalize().mul(delta * speed)
            node.transform.position.add(backward)
        }
        if (Input.isKeyHeld(Keycode.A)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val left = Vector3d(
                -Math.cos(yawRad),
                0.0,
                Math.sin(yawRad)
            )
            left.normalize().mul(delta * speed)
            node.transform.position.add(left)
        }
        if (Input.isKeyHeld(Keycode.D)) {
            val yawRad = Math.toRadians(node.transform.rotation.getYaw())
            val right = Vector3d(
                Math.cos(yawRad),
                0.0,
                -Math.sin(yawRad)
            )
            right.normalize().mul(delta * speed)
            node.transform.position.add(right)
        }

        if (Input.isKeyHeld(Keycode.SPACE)) {
            node.transform.translate(0.0, speed * delta, 0.0)
        }
        if (Input.isKeyHeld(Keycode.SHIFT)) {
            node.transform.translate(0.0, -speed * delta, 0.0)
        }

        val deltaMouse = Input.getMouseDelta()
        if (Input.isMouseButtonPressed(MouseButton.RIGHT)) {
            node.transform.rotation.addYaw(deltaMouse.x * 0.1)
            node.transform.rotation.addPitch(deltaMouse.y * 0.1)
        }

        if (Input.hasPressedKey(Keycode.G)) {
            val node = Node("Node", engine.root, Transform(0.0, 0.0, -5.0))
            val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
            val texture = Loader.loadTexture("texture/map.png")
            val mesh = Loader.loadObj("/model/cube.obj")
            node.addComponent(texture)
            node.addComponent(mesh)
            node.addComponent(shader)

            engine
        }
    }

    override fun render() {}
    override fun cleanup() {}
}