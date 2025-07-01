package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.node.MeshNode
import me.johanrong.glare.node.Node
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30

class MeshRenderer (val engine: GlareEngine) : IRenderer {
    val shader = Shader("/shader/mesh.vert", "/shader/mesh.frag")

    init {
        shader.createUniform("viewMatrix")
        shader.createUniform("projectionMatrix")
        shader.createUniform("transformMatrix")
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
            if (child is MeshNode) {
                GL30.glBindVertexArray(child.getMesh().getId())
                GL20.glEnableVertexAttribArray(0)
                GL20.glEnableVertexAttribArray(1)
                GL20.glEnableVertexAttribArray(2)

                shader.setUniform("viewMatrix", engine.camera!!.transform.getViewMatrix())
                shader.setUniform("transformMatrix", engine.camera!!.transform.getTransformMatrix())

                GL11.glDrawElements(GL11.GL_TRIANGLES, child.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0)
                GL20.glDisableVertexAttribArray(0)
                GL20.glDisableVertexAttribArray(1)
                GL20.glDisableVertexAttribArray(2)
                GL30.glBindVertexArray(0)
            }
            renderChildren(child)
        }
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}