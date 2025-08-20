package me.johanrong.glare.engine.core

import me.johanrong.glare.engine.util.loadImage
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWImage
import org.lwjgl.opengl.GL46
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil

class Window (
    private var title: String,
    var width: Int,
    var height: Int,
    var maximized: Boolean,
    var vSync: Boolean,
    var fov: Double = 70.0,
    private var iconPaths: List<String>? = listOf("me/johanrong/glare/assets/glare_icon.png")
) {
    private var handle: Long
    private var projectionMatrix: Matrix4f = Matrix4f()

    private var opengl: OpenGL? = null

    companion object {
        const val Z_NEAR: Float = 0.01f;
        const val Z_FAR: Float = 1000.0f;
    }

    init {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL46.GL_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL46.GL_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL46.GL_TRUE)

        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, if (maximized) GLFW.GLFW_TRUE else GLFW.GLFW_FALSE)

        handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (handle == MemoryUtil.NULL) {
            throw IllegalStateException("Failed to create the GLFW window")
        }

        GLFW.glfwSetFramebufferSizeCallback(handle) { handle: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            GL46.glViewport(0, 0, width, height)
        }

        GLFW.glfwMakeContextCurrent(handle)

        opengl = OpenGL()

        if (vSync) {
            GLFW.glfwSwapInterval(1)
        } else {
            GLFW.glfwSwapInterval(0)
        }

        if (!iconPaths.isNullOrEmpty()) {
            setIcons(iconPaths!!)
        }

        GLFW.glfwShowWindow(handle)
    }

    fun shouldClose(): Boolean {
        return GLFW.glfwWindowShouldClose(handle)
    }

    fun update() {
        GLFW.glfwSwapBuffers(handle)
        GLFW.glfwPollEvents()
    }

    fun cleanup() {
        GLFW.glfwDestroyWindow(handle)
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
        opengl!!.cleanup()
    }

    fun setTitle(title: String) {
        GLFW.glfwSetWindowTitle(handle, title)
    }

    fun getTitle(): String {
        return title
    }

    fun getHandle(): Long {
        return handle
    }

    fun updateProjectionMatrix(): Matrix4f {
        val aspectRatio = width.toFloat() / height.toFloat()
        return projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, Z_NEAR, Z_FAR)
    }

    private fun setIcons(paths: List<String>) {
        MemoryStack.stackPush().use { stack ->
            val icons = GLFWImage.mallocStack(paths.size, stack)

            for (i in paths.indices) {
                val iconBuffer = loadImage(paths[i], stack)
                if (iconBuffer != null) {
                    val w = stack.mallocInt(1)
                    val h = stack.mallocInt(1)
                    val c = stack.mallocInt(1)

                    val buffer = STBImage.stbi_load_from_memory(iconBuffer, w, h, c, 4)
                    if (buffer != null) {
                        val width = w.get()
                        val height = h.get()

                        val image = GLFWImage.mallocStack(stack)
                        image.set(width, height, buffer)
                        icons.put(image)

                        // Don't free buffer here, GLFW needs it until the program ends
                    }
                }
            }

            icons.flip()
            GLFW.glfwSetWindowIcon(handle, icons)
        }
    }

    fun setWindowIcons(paths: List<String>) {
        if (paths.isNotEmpty()) {
            setIcons(paths)
        }
    }
}