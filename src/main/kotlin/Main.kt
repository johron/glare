package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IRootScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.MeshNode
import me.johanrong.glare.type.Transform
import me.johanrong.glare.util.loadObj

fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = false,
        vSync = true
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
        engine.camera = Freecam(engine.root, Transform(0.0, 0.0, 5.0))

        val mesh = loadObj("/model/bunny.obj")
        val meshNode = MeshNode("Cube", engine.root, mesh)
    }

    override fun update(delta: Double) {
        //println("root update")
        //println(engine.camera?.transform?.position)
    }

    override fun render() {
        //println("Game rendered")
    }

    override fun cleanup() {
        println("Game cleaned up")
    }
}