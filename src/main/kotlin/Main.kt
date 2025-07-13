package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.ModelBuilder
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.NodeBuilder
import me.johanrong.glare.node.component.CameraComponent
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.node.component.IScript
import me.johanrong.glare.node.component.ScriptsComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.DoubleString
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.type.io.MouseButton
import me.johanrong.glare.util.Defaults
import me.johanrong.glare.util.Input
import org.joml.Math
import org.joml.Vector3d
import org.joml.Vector3f

fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = false,
        vSync = true,
    )

    GlareEngine(window, TestGame())
}

class TestGame : IScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    lateinit var input: Input

    override fun init(root: Node) {
        engine = (root.getComponent(Component.ENGINE_REF) as EngineRefComponent).getEngine()
        input = Input(engine)

        val camera = NodeBuilder.go {
            name = "Freecam"
            transform = Transform(Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript()))
            )
        }

        engine.setCamera(camera)

        ModelBuilder.go {
            name = "Node"
            transform = Transform(0.0, 0.0, -5.0)
            parent = root
            mesh = "/model/cube.obj"
            texture = "texture/map.png"
            shader = DoubleString("/shader/mesh.vert", "/shader/mesh.frag")
        }
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)

        if (input.hasPressedKey(Keycode.G)) {
            val camera = engine.getCamera()
            ModelBuilder.go {
                name = "test"
                transform = Transform(camera!!.transform.clone().position, Vector3f(0.1f))
                parent = engine.root
                mesh = "/model/cube.obj"
                shader = DoubleString("/shader/mesh.vert", "/shader/mesh.frag")
            }
        }
    }

    override fun render() {}
    override fun cleanup() {}
}

class FreecamScript(var speed: Double = Defaults.FREECAM_SPEED) : IScript {
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