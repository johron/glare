package me.johanrong.glare.util

import de.javagl.obj.Obj
import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import me.johanrong.glare.type.Mesh
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import java.nio.FloatBuffer
import java.nio.IntBuffer

private val vaos = mutableListOf<Int>()
private val vbos = mutableListOf<Int>()

fun loadText(path: String): String {
    val list = object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()
    return list?.joinToString("\n") ?: throw Exception("Resource not found: $path")
}

fun loadObj(path: String): Mesh {
    val inputStream = object {}.javaClass.getResourceAsStream(path) ?: throw Exception("Resource not found: $path")
    val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))
    inputStream.close()

    return storeMesh(obj)
}

fun storeMesh(obj: Obj): Mesh {
    val id: Int = createVAO()
    storeIndiciesBuffer(ObjData.getFaceVertexIndices(obj))
    storeDataInAttribList(0, 3, ObjData.getVertices(obj))
    storeDataInAttribList(1, 2, ObjData.getTexCoords(obj, 0))
    storeDataInAttribList(2, 3, ObjData.getNormals(obj))
    unbind()

    val vertexCount = ObjData.getFaceVertexIndicesArray(obj).size
    return Mesh(id, vertexCount)
}

private fun createVAO(): Int {
    val id = GL30.glGenVertexArrays()
    vaos.add(id)
    GL30.glBindVertexArray(id)
    return id
}

private fun storeIndiciesBuffer(indices: IntBuffer) {
    val vbo = GL15.glGenBuffers()
    vbos.add(vbo)
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW)
}

private fun storeDataInAttribList(attribNo: Int, vertexCount: Int, data: FloatBuffer) {
    val vbo = GL15.glGenBuffers()
    vbos.add(vbo)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo)
    GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW)
    GL20.glVertexAttribPointer(attribNo, vertexCount, GL11.GL_FLOAT, false, 0, 0)
    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0)
}

private fun unbind() {
    GL30.glBindVertexArray(0)
}

fun cleanup() {
    for (vao in vaos) {
        GL30.glDeleteVertexArrays(vao)
    }

    for (vbo in vbos) {
        GL30.glDeleteBuffers(vbo)
    }
}