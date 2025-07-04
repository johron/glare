package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.MeshComponent
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL13
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class MeshRenderer (val engine: GlareEngine) : IRenderer {
    val shader = Shader("/shader/mesh.vert", "/shader/mesh.frag")

    init {
        shader.createUniform("transformMatrix")
        shader.createUniform("projectionMatrix")
        shader.createUniform("viewMatrix")
        shader.createUniform("textureSampler")
        shader.createUniform("hasTexture")
    }

    override fun render() {
        if (engine.camera == null) {
            return
        }

        shader.bind()
        shader.setUniform("projectionMatrix", engine.window.updateProjectionMatrix())
        renderChildren(engine.root)
        shader.unbind()
    }

    fun renderChildren(parent: Node) {
        for (child in parent.getChildren()) {
            child.getComponent(MeshComponent::class.java)?.let { component ->
                GL30.glBindVertexArray(component.mesh.getId())
                GL20.glEnableVertexAttribArray(0)

                if (component.mesh.getMaterial().getTexture() != null) {
                    GL20.glEnableVertexAttribArray(1)
                    GL20.glActiveTexture(GL13.GL_TEXTURE0)
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, component.mesh.getMaterial().getTexture()!!.getId())
                }

                shader.setUniform("transformMatrix", child.transform.getTransformMatrix())
                shader.setUniform("viewMatrix", engine.camera!!.transform.getViewMatrix())
                shader.setUniform("textureSampler", 0)
                shader.setUniform("hasTexture", if (component.mesh.getMaterial().getTexture() != null) 1 else 0)

                GL11.glDrawElements(GL11.GL_TRIANGLES, component.mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0)
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
                GL20.glDisableVertexAttribArray(0)
                GL20.glDisableVertexAttribArray(1)

                GL30.glBindVertexArray(0)
            }
            renderChildren(child)
        }
    }

    override fun cleanup() {}

}