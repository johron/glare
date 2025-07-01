package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IRootScript
import me.johanrong.glare.core.Window

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
        engine.camera = Freecam(engine.root)
    }

    override fun update(delta: Double) {
        //println("root update")
        println(engine.camera?.transform?.position)
    }

    override fun render() {
        //println("Game rendered")
    }

    override fun cleanup() {
        println("Game cleaned up")
    }
}