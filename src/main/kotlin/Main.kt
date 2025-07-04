package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.base.Freecam
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.util.Loader

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

    override fun init(parent: Node) {
        parent.getComponent(EngineRefComponent::class.java)?.let {
            engine = it.getEngine()
        }
        engine.camera = Freecam(engine.root, Transform(Euler(0.0, 0.0, 0.0)))

        //val texture = loadTexture("texture/uv.png")
        val mesh = Loader.loadObj("/model/cube.obj")
        //mesh.getMaterial().setTexture(texture)
        val node = Node("Node", engine.root, Transform(0.0, 0.0, -5.0))
        node.addComponent(MeshComponent(mesh))
    }

    override fun update(delta: Double) {
        //println("root update")
        //println(engine.camera?.transform?.position)
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)
    }

    override fun render() {
        //println("Game rendered")
    }

    override fun cleanup() {
        println("Game cleaned up")
    }
}