package me.johanrong.glare.core

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.base.Camera
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.render.Renderer
import me.johanrong.glare.util.Constants
import me.johanrong.glare.util.log

class GlareEngine (val window: Window, game: IScript) {
    private var delta = 0.0
    private var isRunning = true

    val root = Node("Root", null)
    var camera: Camera? = null

    private val renderer: Renderer = Renderer(this)

    init {
        log("v${Constants.GLARE_VERSION} - Initialized")

        root.addComponent(EngineRefComponent(this))
        game.init(Node("", null)) // This empty node should never be used, only to make things happy

        var frames = 0
        while (isRunning) {
            val startTime = System.nanoTime()

            if (window.shouldClose()) {
                isRunning = false
            }

            game.update(delta)
            root.update(delta)
            window.update()
            renderer.render()

            val endTime = System.nanoTime()
            delta = (endTime - startTime) / Constants.NANOSECOND.toDouble()

            if (frames % 100 == 0 && delta > 0.0) {
                window.setTitle("${window.getTitle()} - ${(1.0 / delta).toInt()} FPS")
                frames = 0
            }

            frames++
        }

        cleanup()
    }

    private fun cleanup() {
        window.cleanup()
    }

    fun getDelta(): Double {
        return delta
    }
}