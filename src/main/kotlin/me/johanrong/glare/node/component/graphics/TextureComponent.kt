package me.johanrong.glare.node.component.graphics

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.node.component.ComponentType
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL46
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import kotlin.use

class TextureComponent(var path: String) : IComponent {
    override val type = ComponentType.TEXTURE

    private var id: Int = GL46.glGenTextures()

    init {
        val width: Int
        val height: Int
        val buffer: ByteBuffer

        path = if (path.startsWith("/")) path.substring(1) else path

        MemoryStack.stackPush().use { stack ->
            object {}.javaClass.getClassLoader().getResourceAsStream(path).use { res ->
                if (res == null) {
                    throw java.lang.Exception("Resource not found: ${"/$path"}")
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

        GL46.glBindTexture(GL46.GL_TEXTURE_2D, id)
        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1)
        GL46.glTexImage2D(
            GL46.GL_TEXTURE_2D,
            0,
            GL46.GL_RGBA,
            width,
            height,
            0,
            GL46.GL_RGBA,
            GL46.GL_UNSIGNED_BYTE,
            buffer
        )
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D)
        STBImage.stbi_image_free(buffer)
    }

    fun getId(): Int {
        return id
    }

    override fun cleanup() {
        GL46.glDeleteTextures(id)
    }
}