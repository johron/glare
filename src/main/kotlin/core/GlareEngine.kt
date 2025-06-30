package me.johanrong.glare.core

import me.johanrong.glare.render.Renderer
import me.johanrong.glare.util.NANOSECOND

class GlareEngine (val window: Window, game: IGame) {
    private var delta = 0.0
    private var isRunning = true

    private val renderer: Renderer = Renderer(this)

    init {
        game.init(this)

        var frames = 0
        while (isRunning) {
            val startTime = System.nanoTime()

            if (window.shouldClose()) {
                isRunning = false
            }

            game.update(delta)
            window.update()
            renderer.render()

            val endTime = System.nanoTime()
            delta = (endTime - startTime) / NANOSECOND.toDouble()

            if (frames % 10 == 0) {
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