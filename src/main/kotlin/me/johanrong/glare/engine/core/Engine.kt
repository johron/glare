package me.johanrong.glare.engine.core

import imgui.ImGui
import me.johanrong.glare.engine.core.graphics.IGraphics
import me.johanrong.glare.engine.io.Input
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.core.IScript
import me.johanrong.glare.engine.render.ImGuiRenderer
import me.johanrong.glare.engine.render.LightRenderer
import me.johanrong.glare.engine.render.MeshRenderer
import me.johanrong.glare.engine.render.Renderer
import me.johanrong.glare.engine.util.GeneratedConstants
import me.johanrong.glare.engine.util.log

class Engine(val window: Window, val graphics: IGraphics, game: IScript) {
    private var delta = 0.0
    private var isRunning = true
    private var camera: Node? = null
    private var physicsAccumulator = 0.0

    var root = Node.builder {
        name = "Root"
        parent = null
    }

    val input: Input = Input(this)
    val physics: Physics = Physics(this)
    private val renderer: Renderer = Renderer(this)

    companion object {
        const val NANOSECOND: Long = 1_000_000_000L
        const val NAME: String = "Glare"
        const val VERSION: String = GeneratedConstants.GLARE_VERSION
    }

    init {
        log("$VERSION - Initialized")

        ImGui.createContext()

        renderer.addRenderer(LightRenderer(this))
        renderer.addRenderer(MeshRenderer(this))
        renderer.addRenderer(ImGuiRenderer(this))
        //renderer.addRenderer(BillboardTextRenderer(this)) do this later

        root.engine = this
        game.init(root)

        var frames = 0
        while (isRunning) {
            val startTime = System.nanoTime()

            if (window.shouldClose()) {
                isRunning = false
            }

            root.update(delta)
            game.update(delta)
            window.update()
            renderer.render()

            val fixedTimeStep = 1.0 / 60.0
            physicsAccumulator += delta
            while (physicsAccumulator >= fixedTimeStep) {
                physics.update()
                root.fixedUpdate()
                physicsAccumulator -= fixedTimeStep
            }

            val endTime = System.nanoTime()
            delta = (endTime - startTime) / NANOSECOND.toDouble()

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