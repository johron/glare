package me.johanrong.glare.util

import org.lwjgl.opengl.GL46
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

object Mesh {
    val vaos = mutableListOf<Int>()
    val vbos = mutableListOf<Int>()
    val textures = mutableListOf<Int>()

    fun createVAO(): Int {
        val id = GL46.glGenVertexArrays()
        vaos.add(id)
        GL46.glBindVertexArray(id)
        return id
    }

    fun storeIndiciesBuffer(indices: IntBuffer) {
        val vbo = GL46.glGenBuffers()
        vbos.add(vbo)
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, vbo)
        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, indices, GL46.GL_STATIC_DRAW)
    }

    fun storeDataInAttribList(attribNo: Int, vertexCount: Int, data: FloatBuffer) {
        val vbo = GL46.glGenBuffers()
        vbos.add(vbo)
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo)
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, data, GL46.GL_STATIC_DRAW)
        GL46.glVertexAttribPointer(attribNo, vertexCount, GL46.GL_FLOAT, false, 0, 0)
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0)
    }

    fun unbind() {
        GL46.glBindVertexArray(0)
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
            GL46.glDeleteVertexArrays(vao)
        }

        for (vbo in vbos) {
            GL46.glDeleteBuffers(vbo)
        }

        for (texture in textures) {
            GL46.glDeleteTextures(texture)
        }
    }
}