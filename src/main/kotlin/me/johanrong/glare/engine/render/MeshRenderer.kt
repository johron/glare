package me.johanrong.glare.engine.render

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.graphics.MaterialComponent
import me.johanrong.glare.engine.node.component.graphics.MeshComponent
import me.johanrong.glare.engine.node.component.graphics.TextureComponent
import org.lwjgl.opengl.GL46

class MeshRenderer(val engine: Engine) : IRenderer {
    override fun render(node: Node) {
        if (!node.hasComponent(Component.MESH)) return

        val mesh = node.getComponent(Component.MESH) as MeshComponent
        val camera = engine.getCamera()!!
        val shader = engine.getRenderer().currentShader ?: throw IllegalStateException("No shader set for MeshRenderer")

        //glGetError()

        shader.setUniform("projectionMatrix", engine.window.updateProjectionMatrix())

        GL46.glBindVertexArray(mesh.getId())
        GL46.glEnableVertexAttribArray(0)

        if (node.hasComponent(Component.TEXTURE)) {
            val texture = node.getComponent(Component.TEXTURE) as TextureComponent
            GL46.glEnableVertexAttribArray(1)
            GL46.glActiveTexture(GL46.GL_TEXTURE0)
            GL46.glBindTexture(GL46.GL_TEXTURE_2D, texture.getId())
            shader.setUniform("hasTexture", 1)
        } else {
            shader.setUniform("hasTexture", 0)
        }

        if (node.hasComponent(Component.MATERIAL)) {
            val material = node.getComponent(Component.MATERIAL) as MaterialComponent
            material.applyToShader(shader)
        }

        shader.setUniform("transformMatrix", node.transform.getTransformMatrix())
        shader.setUniform("viewMatrix", camera.transform.getViewMatrix())
        shader.setUniform("textureSampler", 0)

        //val error = glGetError()
        //if (error != GL_NO_ERROR) {
        //    println("1OpenGL error: $error")
        //    //continue
        //}

        GL46.glDrawElements(GL46.GL_TRIANGLES, mesh.getVertexCount(), GL46.GL_UNSIGNED_INT, 0)

        //val error2 = glGetError()
        //if (error2 != GL_NO_ERROR) {
        //    println("2OpenGL error: $error2")
        //}

        GL46.glBindTexture(GL46.GL_TEXTURE_2D, 0)
        GL46.glDisableVertexAttribArray(0)
        GL46.glDisableVertexAttribArray(1)

        GL46.glBindVertexArray(0)
    }

    override fun cleanup() {}
    override fun init() {}
}