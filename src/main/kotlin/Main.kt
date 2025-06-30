package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IScript
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

class TestGame () : IScript  {
    var engine: GlareEngine? = null

    override fun init(engine: GlareEngine?) {
        this.engine = engine
        println("Game initialized with engine: $engine")
    }

    override fun update(delta: Double) {
        println("Game updated with delta: $delta")
    }

    override fun render() {
        println("Game rendered")
    }

    override fun cleanup() {
        println("Game cleaned up")
    }
}