package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.graphics.ShaderComponent
import org.lwjgl.opengl.GL46

class Renderer(val engine: Engine) {
    private val renderers = mutableListOf<IRenderer>()

    var currentShader: ShaderComponent? = null

    fun render() {
        clear()

        if (engine.getCamera() == null) {
            return
        }

        for (renderer in renderers) {
            renderer.init()
        }

        for (node in engine.root.getChildren()) {
            if (node.hasComponent(Component.SHADER)) {
                currentShader = (node.getComponent(Component.SHADER)) as ShaderComponent
                currentShader?.bind()
            } else {
                currentShader?.unbind()
                currentShader = null
            }

            val sortedRenderers = renderers.sortedBy { it.priority }
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