package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.node.component.mesh.TextureComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.node.component.mesh.ShaderComponent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class MeshRenderer (val engine: GlareEngine) : IRenderer {
    override fun render() {
        if (engine.camera == null) {
            return
        }

        renderChildren(engine.root)
    }

    fun renderChildren(parent: Node) {
        for (child in parent.getChildren()) {
            if (child.hasComponent(Component.MESH)) {
                val mesh = child.getComponent(Component.MESH) as MeshComponent
                val shader = (child.getComponent(Component.SHADER) ?:
                    throw Exception("For now MeshRenderer requires a ShaderComponent to render meshes, " +
                            "in the future shaders will be automatic and done differently"))
                        as ShaderComponent

                shader.bind()
                shader.setUniform("projectionMatrix", engine.window.updateProjectionMatrix())

                GL30.glBindVertexArray(mesh.getId())
                GL20.glEnableVertexAttribArray(0)

                if (child.hasComponent(Component.TEXTURE)) {
                    val texture = child.getComponent(Component.TEXTURE) as TextureComponent
                    GL20.glEnableVertexAttribArray(1)
                    GL20.glActiveTexture(GL13.GL_TEXTURE0)
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId())
                    shader.setUniform("hasTexture", 1)
                } else {
                    shader.setUniform("hasTexture", 0)
                }

                shader.setUniform("transformMatrix", child.transform.getTransformMatrix())
                shader.setUniform("viewMatrix", engine.camera!!.transform.getViewMatrix())
                shader.setUniform("textureSampler", 0)

                GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0)
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
                GL20.glDisableVertexAttribArray(0)
                GL20.glDisableVertexAttribArray(1)

                GL30.glBindVertexArray(0)
                shader.unbind()
            }
            renderChildren(child)
        }
    }

    override fun cleanup() {}

}