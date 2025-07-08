package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.util.Mesh.textures
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL30
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import kotlin.use

class TextureComponent(path: String) : IComponent {
    override val type = Component.TEXTURE

    private var id: Int = GL11.glGenTextures()

    init {
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
    }

    fun getId(): Int {
        return id
    }
}