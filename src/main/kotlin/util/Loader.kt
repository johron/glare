package me.johanrong.glare.util

import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.node.component.mesh.TextureComponent
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL15
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

object Loader {
    private val vaos = mutableListOf<Int>()
    private val vbos = mutableListOf<Int>()
    private val textures = mutableListOf<Int>()

    fun loadPlain(path: String): String {
        val list = object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()
        return list?.joinToString("\n") ?: throw Exception("Resource not found: $path")
    }

    fun loadObj(path: String): MeshComponent {
        val inputStream = object {}.javaClass.getResourceAsStream(path) ?: throw Exception("Resource not found: $path")
        val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))

        val indices = ObjData.getFaceVertexIndices(obj)
        val vertices = ObjData.getVertices(obj)
        val texCoords = ObjData.getTexCoords(obj, 2)
        val texCoordsArray = ObjData.getTexCoordsArray(obj, 2)

        val id: Int = createVAO()
        storeIndiciesBuffer(indices)
        storeDataInAttribList(0, 3, vertices)
        if (!texCoordsArray.isEmpty()) {
            storeDataInAttribList(1, 2, texCoords)
        }
        //storeDataInAttribList(2, 3, ObjData.getNormals(obj))
        unbind()
        inputStream.close()

        val vertexCount = ObjData.getFaceVertexIndicesArray(obj).size
        return MeshComponent(id, vertexCount)
    }

    fun loadTexture(path: String): TextureComponent {
        val width: Int
        val height: Int
        val buffer: ByteBuffer

        MemoryStack.stackPush().use { stack ->
            object {}.javaClass.getClassLoader().getResourceAsStream(path).use { res ->
                if (res == null) {
                    throw java.lang.Exception("Resource not found: " + path)
                }
                val bytes: ByteArray = res.readAllBytes()
                val imageBuffer = BufferUtils.createByteBuffer(bytes.size)
                imageBuffer.put(bytes)
                imageBuffer.flip()

                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val c = stack.mallocInt(1)

                buffer = STBImage.stbi_load_from_memory(imageBuffer, w, h, c, 4)!!

                if (buffer == null) {
                    throw java.lang.Exception("Could not load texture file: " + path + ": " + STBImage.stbi_failure_reason())
                }

                width = w.get()
                height = h.get()
            }
        }

        val id = GL11.glGenTextures()
        textures.add(id)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id)
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1)
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA,
            width,
            height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            buffer
        )
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D)
        STBImage.stbi_image_free(buffer)
        return TextureComponent(id)
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

        for (texture in textures) {
            GL11.glDeleteTextures(texture)
        }
    }
}