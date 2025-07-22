package me.johanrong.glare.core.graphics

import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL46
import org.lwjgl.opengl.GLDebugMessageCallback

class OpenGL : IGraphics {
    init {
        GL.createCapabilities();

        GL46.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GL46.glEnable(GL46.GL_DEPTH_TEST)
        GL46.glEnable(GL46.GL_STENCIL_TEST)
        GL46.glEnable(GL46.GL_CULL_FACE)
        GL46.glCullFace(GL46.GL_BACK)

        GL46.glEnable(GL46.GL_DEBUG_OUTPUT)
        GL46.glDebugMessageCallback({ source, type, id, severity, length, message, userParam ->
            val msg = GLDebugMessageCallback.getMessage(length, message)
            println("OpenGL Error: $msg")
        }, 0)
    }

    override fun cleanup() {
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT or GL46.GL_DEPTH_BUFFER_BIT)
        GL46.glDisable(GL46.GL_DEPTH_TEST)
        GL46.glDisable(GL46.GL_STENCIL_TEST)
        GL46.glDisable(GL46.GL_CULL_FACE)
        GL46.glBindVertexArray(0)

        GL.setCapabilities(null)
    }
}