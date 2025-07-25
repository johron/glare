package me.johanrong.glare.core

import me.johanrong.glare.core.graphics.IGraphics
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.core.EngineRefComponent
import me.johanrong.glare.node.component.core.IScript
import me.johanrong.glare.render.MeshRenderer
import me.johanrong.glare.render.Renderer
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.render.LightRenderer
import me.johanrong.glare.util.GeneratedConstants
import me.johanrong.glare.util.log

class Engine(val window: Window, val graphics: IGraphics, game: IScript) {
    private var delta = 0.0
    private var isRunning = true
    private var camera: Node? = null

    var root = Node.builder {
        name = "Root"
        parent = null
        components = mutableListOf(EngineRefComponent(this@Engine))
    }

    private val renderer: Renderer = Renderer(this)

    companion object {
        const val NANOSECOND: Long = 1_000_000_000L
        const val NAME: String = "Glare"
        const val VERSION: String = GeneratedConstants.GLARE_VERSION
    }

    init {
        log("v${VERSION} - Initialized")

        renderer.addRenderer(MeshRenderer(this))
        renderer.addRenderer(LightRenderer(this))

        game.init(root)

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
        log("Cleaning up engine resources")

        root.destroy()

        renderer.cleanup()
        window.cleanup()
        graphics.cleanup()
    }

    fun destroy() {
        log("Destroying engine")
        isRunning = false
    }

    fun getDelta(): Double {
        return delta
    }

    fun getCamera(exception: Boolean = false): Node? {
        if (camera == null && exception) {
            throw Exception("No camera set in the engine")
        }
        return camera
    }

    fun setCamera(camera: Node) {
        if (camera.hasComponent(Component.CAMERA)) {
            this.camera = camera
            log("Camera set to ${camera.name}")
        } else {
            throw Exception("Could not set camera: Node does not have a Camera component")
        }
    }

    fun getRenderer(): Renderer {
        return renderer
    }
}