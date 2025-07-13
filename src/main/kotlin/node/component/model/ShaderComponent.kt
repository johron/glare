package me.johanrong.glare.node.component.model

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.render.Shader
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.DoubleString
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL46
import org.lwjgl.system.MemoryStack

class ShaderComponent(vertexPath: String, fragmentPath: String) : IComponent {
    override val type = Component.SHADER

    val programId: Int = GL46.glCreateProgram()

    private val vertexShaderId: Int
    private val fragmentShaderId: Int
    private val uniforms: MutableMap<String, Int> = HashMap()

    constructor(doubleString: DoubleString) : this(doubleString.x, doubleString.y)

    init {
        if (programId == 0) {
            throw Exception("Could not create shader program")
        }

        vertexShaderId = createShader(Shader.makeVertex(vertexPath), GL46.GL_VERTEX_SHADER)
        fragmentShaderId = createShader(Shader.makeFragment(fragmentPath), GL46.GL_FRAGMENT_SHADER)
        link()

        createUniform("transformMatrix")
        createUniform("projectionMatrix")
        createUniform("viewMatrix")
        createUniform("textureSampler")
        createUniform("hasTexture")
    }

    fun createUniform(name: String) {
        val location = GL46.glGetUniformLocation(programId, name)
        if (location < 0) {
            throw Exception("Could not find uniform: $name")
        }
        uniforms.put(name, location)
    }

    fun setUniform(uniformName: String?, value: Vector4f) {
        GL46.glUniform4f(uniforms[uniformName]!!, value.x, value.y, value.z, value.w)
    }

    fun setUniform(uniformName: String?, value: Vector3f) {
        GL46.glUniform3f(uniforms[uniformName]!!, value.x, value.y, value.z)
    }

    fun setUniform(uniformName: String?, value: Int) {
        GL46.glUniform1i(uniforms[uniformName]!!, value)
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            GL46.glUniformMatrix4fv(
                uniforms[uniformName]!!, false,
                value.get(stack.mallocFloat(16))
            )
        }
    }

    fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = GL46.glCreateShader(shaderType)

        if (shaderId == 0) {
            throw Exception("Could not create Shader of type $shaderType")
        }

        GL46.glShaderSource(shaderId, shaderCode)
        GL46.glCompileShader(shaderId)

        if (GL46.glGetShaderi(shaderId, GL46.GL_COMPILE_STATUS) == 0) {
            throw java.lang.Exception(
                "Could not compile Shader Code of type $shaderType: " +
                        GL46.glGetShaderInfoLog(shaderId, 1024)
            )
        }

        GL46.glAttachShader(programId, shaderId)

        return shaderId
    }

    fun link() {
        GL46.glLinkProgram(programId)
        if (GL46.glGetProgrami(programId, GL46.GL_LINK_STATUS) == 0) {
            throw Exception("Could not link Shader Code: " + GL46.glGetProgramInfoLog(programId, 1024))
        }

        if (vertexShaderId != 0) {
            GL46.glDetachShader(programId, vertexShaderId)
            GL46.glDeleteShader(vertexShaderId)
        }

        if (fragmentShaderId != 0) {
            GL46.glDetachShader(programId, fragmentShaderId)
            GL46.glDeleteShader(fragmentShaderId)
        }

        GL46.glValidateProgram(programId)
        if (GL46.glGetProgrami(programId, GL46.GL_VALIDATE_STATUS) == 0) {
            throw Exception("Could not validate Shader Code: " + GL46.glGetProgramInfoLog(programId, 1024))
        }
    }

    fun bind() {
        GL46.glUseProgram(programId)
    }

    fun unbind() {
        GL46.glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            GL46.glDeleteProgram(programId)
        }
    }
}