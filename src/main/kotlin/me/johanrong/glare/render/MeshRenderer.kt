package me.johanrong.glare.render

import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.graphics.MeshComponent
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.graphics.TextureComponent
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.graphics.MaterialComponent
import me.johanrong.glare.node.component.lighting.Light
import me.johanrong.glare.node.component.lighting.LightComponent
import org.lwjgl.opengl.GL11.GL_NO_ERROR
import org.lwjgl.opengl.GL11.glGetError
import org.lwjgl.opengl.GL46

class MeshRenderer (val engine: Engine) : IRenderer {
    var lights = mutableListOf<LightComponent>()

    override fun render() {
        if (engine.getCamera() == null) {
            return
        }

        lights.clear()
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

                glGetError()

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

                if (child.hasComponent(Component.LIGHT)) {
                    val lightComponent = child.getComponent(Component.LIGHT) as LightComponent
                    lights.add(lightComponent)
                    lightComponent.applyToShader(shader, lights.size)
                }

                // set light amount uniforms
                shader.setUniform("uNumDirLights", lights.count { it.lightType == Light.DIRECTIONAL })
                shader.setUniform("uNumPointLights", lights.count { it.lightType == Light.POINT })

                if (child.hasComponent(Component.MATERIAL)) {
                    val material = child.getComponent(Component.MATERIAL) as MaterialComponent
                    material.applyToShader(shader)
                }

                shader.setUniform("transformMatrix", child.transform.getTransformMatrix())
                shader.setUniform("viewMatrix", camera.transform.getViewMatrix())
                shader.setUniform("textureSampler", 0)

                val error = glGetError()
                if (error != GL_NO_ERROR) {
                    println("1OpenGL error: $error")
                    //continue
                }

                GL46.glDrawElements(GL46.GL_TRIANGLES, mesh.getVertexCount(), GL46.GL_UNSIGNED_INT, 0)

                val error2 = glGetError()
                if (error2 != GL_NO_ERROR) {
                    println("2OpenGL error: $error2")
                }

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