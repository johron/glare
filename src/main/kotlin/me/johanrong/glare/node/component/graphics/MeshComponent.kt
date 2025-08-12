package me.johanrong.glare.node.component.graphics

import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import org.lwjgl.opengl.GL46
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

open class MeshComponent(isPrimary: Boolean = true) : IComponent {
    override val type = Component.MESH

    private val id: Int = createVAO()

    private var indices: IntArray? = null
    private var vertices: FloatArray? = null
    private var texCoords: FloatArray? = null

    private var vbos: MutableList<Int> = mutableListOf()

    constructor(indices: IntArray, vertices: FloatArray, texCoords: FloatArray) : this(false) {
        this.indices = indices
        this.vertices = vertices
        this.texCoords = texCoords

        load()
    }

    constructor(path: String) : this(false) {
        val inputStream = object {}.javaClass.getResourceAsStream(path) ?: throw Exception("Resource not found: $path")
        val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))
        inputStream.close()

        this.indices = ObjData.getFaceVertexIndicesArray(obj)
        this.vertices = ObjData.getVerticesArray(obj)
        this.texCoords = ObjData.getTexCoordsArray(obj, 2)

        load()
    }

    init {
        if (isPrimary) {
            throw Exception("Cannot use the primary constructor")
        }
    }

    private fun load() {
        val indicesBuffer = storeDataArrayInBuffer(indices)
        storeIndicesBuffer(indicesBuffer)
        MemoryUtil.memFree(indicesBuffer)

        val verticesBuffer = storeDataArrayInBuffer(vertices)
        storeDataInAttribList(0, 3, verticesBuffer)
        MemoryUtil.memFree(verticesBuffer)

        if (texCoords != null && texCoords!!.isNotEmpty()) {
            val texCoordsBuffer = storeDataArrayInBuffer(texCoords)
            storeDataInAttribList(1, 2, texCoordsBuffer)
            MemoryUtil.memFree(texCoordsBuffer)
        }

        unbind()
    }

    fun getId(): Int {
        return id
    }

    fun getVertexCount(): Int {
        return indices?.size ?: throw Exception("Indices not set for MeshComponent")
    }

    private fun createVAO(): Int {
        val id = GL46.glGenVertexArrays()
        GL46.glBindVertexArray(id)
        return id
    }

    private fun storeIndicesBuffer(indices: IntBuffer) {
        val vbo = GL46.glGenBuffers()
        vbos.add(vbo)
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, vbo)
        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, indices, GL46.GL_STATIC_DRAW)
    }

    private fun storeDataInAttribList(attribNo: Int, vertexCount: Int, data: FloatBuffer) {
        val vbo = GL46.glGenBuffers()
        vbos.add(vbo)
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vbo)
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER, data, GL46.GL_STATIC_DRAW)
        GL46.glVertexAttribPointer(attribNo, vertexCount, GL46.GL_FLOAT, false, 0, 0)
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0)
    }

    private fun unbind() {
        GL46.glBindVertexArray(0)
    }

    private fun storeDataArrayInBuffer(data: FloatArray?): FloatBuffer {
        val buffer = MemoryUtil.memAllocFloat(data!!.size)
        buffer.put(data).flip()
        return buffer
    }

    private fun storeDataArrayInBuffer(data: IntArray?): IntBuffer {
        val buffer = MemoryUtil.memAllocInt(data!!.size)
        buffer.put(data).flip()
        return buffer
    }

    override fun cleanup() {
        GL46.glDeleteVertexArrays(id)

        for (vbo in vbos) {
            GL46.glDeleteBuffers(vbo)
        }

        unbind()
    }
}