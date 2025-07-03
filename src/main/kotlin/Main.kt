package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IRootScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.MeshNode
import me.johanrong.glare.node.base.Freecam
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.util.Loader

fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = true,
        vSync = true,
    )

    GlareEngine(window, TestGame())
}

class TestGame : IRootScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    override fun init(engine: GlareEngine) {
        println("Game initialized with engine: $engine")

        TestGame.engine = engine
        engine.camera = Freecam(engine.root, Transform(Euler(0.0, 0.0, 0.0)))

        //val texture = loadTexture("texture/uv.png")
        val mesh = Loader.loadObj("/model/Untitled.obj")
        //mesh.getMaterial().setTexture(texture)
        val meshNode = MeshNode("Node", engine.root, mesh, Transform(0.0, 0.0, -5.0))
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