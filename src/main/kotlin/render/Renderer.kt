package me.johanrong.glare.render

import me.johanrong.glare.core.Engine
import org.lwjgl.opengl.GL46

class Renderer (var engine: Engine) : IRenderer {
    private val renderers = mutableListOf<IRenderer>()

    override fun render() {
        clear()

        for (renderer in renderers) {
            renderer.render()
        }
    }

    fun clear() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT or GL46.GL_DEPTH_BUFFER_BIT)
    }

    fun addRenderer(renderer: IRenderer) {
        renderers.add(renderer)
    }

    override fun cleanup() {
        for (renderer in renderers) {
            renderer.cleanup()
        }
        renderers.clear()
    }
}