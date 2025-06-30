package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IGame
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

class TestGame : IGame {
    private lateinit var engine: GlareEngine

    override fun init(engine: GlareEngine) {
        this.engine = engine
        println("Game initialized")
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