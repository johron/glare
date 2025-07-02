package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.node.MeshNode
import me.johanrong.glare.node.Node
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
            println("Rendering child: ${child.name} of type ${child::class.simpleName}")
            if (child is MeshNode) {
                println(".")
                println(".")
                GL30.glBindVertexArray(child.getMesh().getId())
                GL20.glEnableVertexAttribArray(0)
                GL20.glEnableVertexAttribArray(1)
                //GL20.glEnableVertexAttribArray(2)
                println(".")


                println(".")
                if (child.getMesh().getMaterial().getTexture() != null) {
                    println(".")
                    GL20.glActiveTexture(GL13.GL_TEXTURE0)
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, child.getMesh().getMaterial().getTexture()!!.getId())
                }
                println(".")

                shader.setUniform("transformMatrix", child.transform.getTransformMatrix())
                shader.setUniform("viewMatrix", engine.camera!!.transform.getViewMatrix())
                shader.setUniform("textureSampler", 0)
                shader.setUniform("hasTexture", if (child.getMesh().getMaterial().getTexture() != null) 1 else 0)
                println(".")

                GL11.glDrawElements(GL11.GL_TRIANGLES, child.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0)
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
                GL20.glDisableVertexAttribArray(0)
                GL20.glDisableVertexAttribArray(1)
                //GL20.glDisableVertexAttribArray(2)

                println(".")
                GL30.glBindVertexArray(0)
                println(".")
            }
            renderChildren(child)
        }
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}