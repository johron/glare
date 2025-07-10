package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.NodeBuilder
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.node.component.mesh.ShaderComponent
import me.johanrong.glare.node.component.mesh.TextureComponent
import me.johanrong.glare.node.prefab.Freecam
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
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
        engine.setCamera(Freecam(engine.root, Transform(Euler(0.0, 0.0, -90.0))))

        val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
        val texture = TextureComponent("texture/map.png")
        val mesh = MeshComponent("/model/cube.obj")

        val node = NodeBuilder.go {
            name = "Node"
            transform = Transform(0.0, 0.0, -5.0)
            parent = root
            components = mutableListOf(shader, texture, mesh)
        }

        val node2 = NodeBuilder.go {
            name = "Node2"
            transform = Transform(0.0, 2.0, -5.0)
            parent = root
            components = mutableListOf(shader, texture, mesh)
        }
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)

        if (Input.hasPressedKey(Keycode.G)) {
            val camera = engine.getCamera()
            val node = Node("test", engine.root, Transform(camera!!.transform.clone().position, Vector3f(0.1f)))
            val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
            val mesh = MeshComponent("/model/cube.obj")
            node.addComponent(mesh)
            node.addComponent(shader)
        }
    }

    override fun render() {}
    override fun cleanup() {}
}