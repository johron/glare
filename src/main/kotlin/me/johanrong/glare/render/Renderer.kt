package me.johanrong.glare.render

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.component.graphics.ShaderComponent
import org.lwjgl.opengl.GL46

class Renderer(val engine: Engine) {
    private val renderers = mutableListOf<IRenderer>()

    var currentShader: ShaderComponent? = null

    fun render() {
        clear()

        if (engine.getCamera() == null) {
            return
        }

        currentShader = null

        for (renderer in renderers) {
            renderer.init()
        }

        for (node in engine.root.getChildren()) {
            val sortedRenderers = renderers.sortedBy { it.rpriority }
            for (renderer in sortedRenderers) {
                renderer.render(node)
            }
        }
    }

    fun clear() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT or GL46.GL_DEPTH_BUFFER_BIT)
    }

    fun addRenderer(renderer: IRenderer) {
        renderers.add(renderer)
    }

    fun cleanup() {
        for (renderer in renderers) {
            renderer.cleanup()
        }
        renderers.clear()
    }
}