package me.johanrong.glare.engine.component.graphics

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.scripting.Exported
import me.johanrong.glare.engine.type.Color
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL46
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import kotlin.use

class TextureComponent() : IComponent {
    override val type = Component.TEXTURE
    override var node: Node? = null

    @Exported var path: String? = null
    @Exported var color: Color = Color(1f)

    private var id: Int = GL46.glGenTextures()
    private var textureData: ByteBuffer? = null
    private var imageBuffer: ByteBuffer? = null

    private var width: Int = 0
    private var height: Int = 0

//    fun loadTexture() {
//        if (path != null) {
//            path = if (path!!.startsWith("/")) path!!.substring(1) else path
//        } else {
//            return
//        }
//
//        MemoryStack.stackPush().use { stack ->
//            object {}.javaClass.getClassLoader().getResourceAsStream(path).use { res ->
//                if (res == null) {
//                    throw Exception("Resource not found: ${"/$path"}")
//                }
//                val bytes: ByteArray = res.readAllBytes()
//                textureData = BufferUtils.createByteBuffer(bytes.size)
//                textureData?.put(bytes)
//                textureData?.flip()
//
//                val w = stack.mallocInt(1)
//                val h = stack.mallocInt(1)
//                val c = stack.mallocInt(1)
//
//                imageBuffer = STBImage.stbi_load_from_memory(textureData!!, w, h, c, 4)!!
//
//                if (imageBuffer == null) {
//                    throw java.lang.Exception("Could not load texture file: " + path + ": " + STBImage.stbi_failure_reason())
//                }
//
//                width = w.get()
//                height = h.get()
//            }
//        }
//
//        GL46.glBindTexture(GL46.GL_TEXTURE_2D, id)
//        GL46.glPixelStorei(GL46.GL_UNPACK_ALIGNMENT, 1)
//        GL46.glTexImage2D(
//            GL46.GL_TEXTURE_2D,
//            0,
//            GL46.GL_RGBA,
//            width,
//            height,
//            0,
//            GL46.GL_RGBA,
//            GL46.GL_UNSIGNED_BYTE,
//            imageBuffer
//        )
//        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D)
//        STBImage.stbi_image_free(imageBuffer)
//    }

    fun loadTexture() {
        if (path != null) {
            path = if (path!!.startsWith("/")) path!!.substring(1) else path
        } else {
            return
        }

        MemoryStack.stackPush().use { stack ->
            object {}.javaClass.getClassLoader().getResourceAsStream(path).use { res ->
                if (res == null) {
                    throw Exception("Resource not found: ${"/$path"}")
                }
                val bytes: ByteArray = res.readAllBytes()
                textureData = BufferUtils.createByteBuffer(bytes.size)
                textureData?.put(bytes)
                textureData?.flip()

                val w = stack.mallocInt(1)
                val h = stack.mallocInt(1)
                val c = stack.mallocInt(1)

                val originalBuffer = STBImage.stbi_load_from_memory(textureData!!, w, h, c, 4)!!

                if (originalBuffer == null) {
                    throw java.lang.Exception("Could not load texture file: " + path + ": " + STBImage.stbi_failure_reason())
                }

                width = w.get()
                height = h.get()

                // Create a new buffer to hold the tinted image
                imageBuffer = BufferUtils.createByteBuffer(width * height * 4)

                // Copy and apply color tint to each pixel
                originalBuffer.rewind()

                for (i in 0 until width * height) {
                    val r = (originalBuffer.get().toInt()) * 255f * color.red
                    val g = (originalBuffer.get().toInt()) * 255f * color.green
                    val b = (originalBuffer.get().toInt()) * 255f * color.blue

                    imageBuffer!!.put((r * 255).toInt().toByte())
                    imageBuffer!!.put((g * 255).toInt().toByte())
                    imageBuffer!!.put((b * 255).toInt().toByte())
                    //imageBuffer!!.put((a * 255).toInt().toByte())
                }

                imageBuffer!!.flip()

                // Free the original buffer
                STBImage.stbi_image_free(originalBuffer)
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
            imageBuffer
        )
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D)
    }

    fun update() {
        textureData?.let { STBImage.stbi_image_free(it) }
        loadTexture()
    }

    fun getId(): Int {
        return id
    }

    override fun cleanup() {
        GL46.glDeleteTextures(id)
    }
}