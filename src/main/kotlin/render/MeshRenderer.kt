package me.johanrong.glare.render

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.model.MeshComponent
import me.johanrong.glare.node.component.model.ShaderComponent
import me.johanrong.glare.node.component.model.TextureComponent
import me.johanrong.glare.type.Component
import org.lwjgl.opengl.GL46

class MeshRenderer (val engine: Engine) : IRenderer {
    override fun render() {
        if (engine.getCamera() == null) {
            return
        }

        renderChildren(engine.root)
    }

    fun renderChildren(parent: Node) {
        val camera = engine.getCamera()!!
        for (child in parent.getChildren()) {
            if (child.hasComponent(Component.MESH)) {
                val mesh = child.getComponent(Component.MESH) as MeshComponent
                val shader = (child.getComponent(Component.SHADER) ?:
                    throw Exception("For now MeshRenderer requires a ShaderComponent to render meshes, " +
                            "in the future shaders will be automatic and done differently"))
                        as ShaderComponent

                shader.bind()
                shader.setUniform("projectionMatrix", engine.window.updateProjectionMatrix())

                GL46.glBindVertexArray(mesh.getId())
                GL46.glEnableVertexAttribArray(0)

                if (child.hasComponent(Component.TEXTURE)) {
                    val texture = child.getComponent(Component.TEXTURE) as TextureComponent
                    GL46.glEnableVertexAttribArray(1)
                    GL46.glActiveTexture(GL46.GL_TEXTURE0)
                    GL46.glBindTexture(GL46.GL_TEXTURE_2D, texture.getId())
                    shader.setUniform("hasTexture", 1)
                } else {
                    shader.setUniform("hasTexture", 0)
                }

                shader.setUniform("transformMatrix", child.transform.getTransformMatrix())
                shader.setUniform("viewMatrix", camera.transform.getViewMatrix())
                shader.setUniform("textureSampler", 0)

                GL46.glDrawElements(GL46.GL_TRIANGLES, mesh.getVertexCount(), GL46.GL_UNSIGNED_INT, 0)
                GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0)
                GL46.glDisableVertexAttribArray(0)
                GL46.glDisableVertexAttribArray(1)

                GL46.glBindVertexArray(0)
                shader.unbind()
            }
            renderChildren(child)
        }
    }

    override fun cleanup() {}
}