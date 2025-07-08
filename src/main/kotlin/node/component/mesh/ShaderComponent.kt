package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.util.loadPlain
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack

class ShaderComponent(vertexPath: String, fragmentPath: String) : IComponent {
    override val type = Component.SHADER

    val programId: Int = GL20.glCreateProgram()

    private val vertexShaderId: Int
    private val fragmentShaderId: Int
    private val uniforms: MutableMap<String, Int> = HashMap()

    init {
        if (programId == 0) {
            throw Exception("Could not create shader program")
        }

        vertexShaderId = createShader(loadPlain(vertexPath), GL20.GL_VERTEX_SHADER)
        fragmentShaderId = createShader(loadPlain(fragmentPath), GL20.GL_FRAGMENT_SHADER)
        link()

        createUniform("transformMatrix")
        createUniform("projectionMatrix")
        createUniform("viewMatrix")
        createUniform("textureSampler")
        createUniform("hasTexture")
    }

    fun createUniform(name: String) {
        val location = GL20.glGetUniformLocation(programId, name)
        if (location < 0) {
            throw Exception("Could not find uniform: $name")
        }
        uniforms.put(name, location)
    }

    fun setUniform(uniformName: String?, value: Vector4f) {
        GL20.glUniform4f(uniforms[uniformName]!!, value.x, value.y, value.z, value.w)
    }

    fun setUniform(uniformName: String?, value: Vector3f) {
        GL20.glUniform3f(uniforms[uniformName]!!, value.x, value.y, value.z)
    }

    fun setUniform(uniformName: String?, value: Int) {
        GL20.glUniform1i(uniforms[uniformName]!!, value)
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            GL20.glUniformMatrix4fv(
                uniforms[uniformName]!!, false,
                value.get(stack.mallocFloat(16))
            )
        }
    }

    fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = GL20.glCreateShader(shaderType)

        if (shaderId == 0) {
            throw Exception("Could not create Shader of type $shaderType")
        }

        GL20.glShaderSource(shaderId, shaderCode)
        GL20.glCompileShader(shaderId)

        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw java.lang.Exception(
                "Could not compile Shader Code of type $shaderType: " +
                        GL20.glGetShaderInfoLog(shaderId, 1024)
            )
        }

        GL20.glAttachShader(programId, shaderId)

        return shaderId
    }

    fun link() {
        GL20.glLinkProgram(programId)
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw Exception("Could not link Shader Code: " + GL20.glGetProgramInfoLog(programId, 1024))
        }

        if (vertexShaderId != 0) {
            GL20.glDetachShader(programId, vertexShaderId)
            GL20.glDeleteShader(vertexShaderId)
        }

        if (fragmentShaderId != 0) {
            GL20.glDetachShader(programId, fragmentShaderId)
            GL20.glDeleteShader(fragmentShaderId)
        }

        GL20.glValidateProgram(programId)
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            throw Exception("Could not validate Shader Code: " + GL20.glGetProgramInfoLog(programId, 1024))
        }
    }

    fun bind() {
        GL20.glUseProgram(programId)
    }

    fun unbind() {
        GL20.glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            GL20.glDeleteProgram(programId)
        }
    }
}