package me.johanrong.glare.node.component.graphics

import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.render.Shader
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL46
import org.lwjgl.system.MemoryStack

class ShaderComponent(
    vertexPath: String? = null,
    fragmentPath: String? = null,
    geometryPath: String? = null,
    controlPath: String? = null,
    evalPath: String? = null,
    computePath: String? = null
) : IComponent {
    override val type = Component.SHADER
    override var node: Node? = null

    val programId: Int = GL46.glCreateProgram()

    private val shaderIds: MutableMap<Int, Int> = HashMap()
    private val uniforms: MutableMap<String, Int> = HashMap()

    init {
        if (programId == 0) {
            throw Exception("Could not create shader program")
        }

        if (vertexPath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_VERTEX_SHADER, createShader(Shader.makeVertex(vertexPath), GL46.GL_VERTEX_SHADER))
        }
        if (fragmentPath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_FRAGMENT_SHADER, createShader(Shader.makeFragment(fragmentPath), GL46.GL_FRAGMENT_SHADER))
        }
        if (geometryPath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_GEOMETRY_SHADER, createShader(Shader.makeGeometry(geometryPath), GL46.GL_GEOMETRY_SHADER))
        }
        if (controlPath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_TESS_CONTROL_SHADER, createShader(Shader.makeControl(controlPath), GL46.GL_TESS_CONTROL_SHADER))
        }
        if (evalPath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_TESS_EVALUATION_SHADER, createShader(Shader.makeEval(evalPath), GL46.GL_TESS_EVALUATION_SHADER))
        }
        if (computePath?.isEmpty() == false) {
            shaderIds.put(GL46.GL_COMPUTE_SHADER, createShader(Shader.makeCompute(computePath), GL46.GL_COMPUTE_SHADER))
        }

        link()

        createUniform("transformMatrix")
        createUniform("projectionMatrix")
        createUniform("viewMatrix")
        createUniform("textureSampler")
        createUniform("hasTexture")
    }

    fun getUniformLocation(name: String): Int {
        return uniforms.getOrPut(name) {
            val location = GL46.glGetUniformLocation(programId, name)
            if (location == -1) {
                println("Warning: Uniform '$name' not found")
            }
            location
        }
    }

    fun createUniform(name: String) {
        val location = GL46.glGetUniformLocation(programId, name)
        //if (location < 0) {
        //    throw Exception("Could not find uniform: $name")
        //}
        uniforms.put(name, location)
    }

    fun setUniform(uniformName: String, value: Vector4f) {
        GL46.glUniform4f(getUniformLocation(uniformName), value.x, value.y, value.z, value.w)
    }

    fun setUniform(uniformName: String, value: Vector3f) {
        GL46.glUniform3f(getUniformLocation(uniformName), value.x, value.y, value.z)
    }

    fun setUniform(uniformName: String, value: Int) {
        GL46.glUniform1i(getUniformLocation(uniformName), value)
    }

    fun setUniform(uniformName: String, value: Float) {
        GL46.glUniform1f(getUniformLocation(uniformName), value)
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            GL46.glUniformMatrix4fv(
                getUniformLocation(uniformName), false,
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

        for ((_, shaderId) in shaderIds) {
            if (shaderId == 0) continue
            GL46.glDetachShader(programId, shaderId)
            GL46.glDeleteShader(shaderId)
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

    override fun cleanup() {
        unbind()
        if (programId != 0) {
            GL46.glDeleteProgram(programId)
        }
    }

    companion object {
        fun builder(block: Builder.() -> Unit): ShaderComponent {
            val builder = Builder()
            builder.block()
            return builder.build()
        }
    }

    class Builder {
        var vertex: String? = null
        var fragment: String? = null
        var geometry: String? = null
        var control: String? = null
        var eval: String? = null
        var compute: String? = null

        fun vertex(value: String) = apply { vertex = value }
        fun fragment(value: String) = apply { fragment = value }
        fun geometry(value: String) = apply { geometry = value }
        fun control(value: String) = apply { control = value }
        fun eval(value: String) = apply { eval = value }
        fun compute(value: String) = apply { compute = value }

        fun build(): ShaderComponent {
            return ShaderComponent(
                vertexPath = vertex,
                fragmentPath = fragment,
                geometryPath = geometry,
                controlPath = control,
                evalPath = eval,
                computePath = compute
            )
        }
    }
}