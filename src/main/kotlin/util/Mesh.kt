package me.johanrong.glare.util

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

object Mesh {
    val vaos = mutableListOf<Int>()
    val vbos = mutableListOf<Int>()
    val textures = mutableListOf<Int>()

    fun createVAO(): Int {
        val id = GL30.glGenVertexArrays()
        vaos.add(id)
        GL30.glBindVertexArray(id)
        return id
    }

    fun storeIndiciesBuffer(indices: IntBuffer) {
        val vbo = GL15.glGenBuffers()
        vbos.add(vbo)
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo)
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW)
    }

    fun storeDataInAttribList(attribNo: Int, vertexCount: Int, data: FloatBuffer) {
        val vbo = GL15.glGenBuffers()
        vbos.add(vbo)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW)
        GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0)
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
    }

    fun unbind() {
        GL30.glBindVertexArray(0)
    }

    fun storeDataArrayInBuffer(data: FloatArray?): FloatBuffer {
        val buffer = MemoryUtil.memAllocFloat(data!!.size)
        buffer.put(data).flip()
        return buffer
    }

    fun storeDataArrayInBuffer(data: IntArray?): IntBuffer {
        val buffer = MemoryUtil.memAllocInt(data!!.size)
        buffer.put(data).flip()
        return buffer
    }

    fun cleanup() {
        for (vao in vaos) {
            GL30.glDeleteVertexArrays(vao)
        }

        for (vbo in vbos) {
            GL30.glDeleteBuffers(vbo)
        }

        for (texture in textures) {
            GL11.glDeleteTextures(texture)
        }
    }
}