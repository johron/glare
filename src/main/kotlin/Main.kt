package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.NodeBuilder
import me.johanrong.glare.node.component.CameraComponent
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.node.component.mesh.ShaderComponent
import me.johanrong.glare.node.component.mesh.TextureComponent
import me.johanrong.glare.node.prefab.ModelBuilder
import me.johanrong.glare.node.prefab.script.FreecamScript
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.DoubleString
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.util.Defaults
import me.johanrong.glare.util.Input
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

    override fun init(root: Node) {
        engine = (root.getComponent(Component.ENGINE_REF) as EngineRefComponent).getEngine()

        val camera = NodeBuilder.go {
            name = "Freecam"
            transform = Transform(Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(CameraComponent())
            script = FreecamScript()
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

        if (Input.hasPressedKey(Keycode.G)) {
            val camera = engine.getCamera()
            ModelBuilder.go {
                name = "test"
                transform = Transform(camera!!.transform.clone().position, Vector3f(0.1f))
                parent = engine.root
                mesh = "/model/cube.obj"
                shader = DoubleString("/shader/mesh.frag", "/shader/mesh.vert")
            }
        }
    }

    override fun render() {}
    override fun cleanup() {}
}