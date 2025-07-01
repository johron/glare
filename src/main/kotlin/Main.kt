package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IRootScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.RootNode

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
        lateinit var root: RootNode
    }

    override fun init(engine: GlareEngine) {
        println("Game initialized with engine: $engine")

        TestGame.engine = engine
        root = RootNode()
        Freecam(root)
    }

    override fun update(delta: Double) {
        root.update(delta)
        //println("root update")
    }

    override fun render() {
        //println("Game rendered")
    }

    override fun cleanup() {
        println("Game cleaned up")
    }
}