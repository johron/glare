package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import org.lwjgl.opengl.GL11

class Renderer (var engine: GlareEngine) : IRenderer {
    var meshRenderer = MeshRenderer(engine)

    override fun render() {
        clear()

        meshRenderer.render()
    }

    fun clear() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
    }

    override fun cleanup() {
        meshRenderer.cleanup()
    }
}