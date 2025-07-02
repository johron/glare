package me.johanrong.glare.core

import me.johanrong.glare.node.RootNode
import me.johanrong.glare.node.base.Camera
import me.johanrong.glare.render.Renderer
import me.johanrong.glare.util.GLARE_VERSION
import me.johanrong.glare.util.NANOSECOND
import me.johanrong.glare.util.log

class GlareEngine (val window: Window, game: IRootScript) {
    private var delta = 0.0
    private var isRunning = true

    val root = RootNode()
    var camera: Camera? = null

    private val renderer: Renderer = Renderer(this)

    init {
        log("v$GLARE_VERSION - Initialized")

        game.init(this)

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
            delta = (endTime - startTime) / NANOSECOND.toDouble()

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