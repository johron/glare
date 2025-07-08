package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import org.lwjgl.opengl.GL11

class Renderer (var engine: GlareEngine) : IRenderer {
    private val renderers = mutableListOf<IRenderer>()

    override fun render() {
        clear()

        for (renderer in renderers) {
            renderer.render()
        }
    }

    fun clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
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