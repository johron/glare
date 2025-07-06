package me.johanrong.glare.core

import me.johanrong.glare.util.Constants
import me.johanrong.glare.util.Defaults
import org.joml.Matrix4f
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryUtil

class Window (
    private var title: String,
    var width: Int,
    var height: Int,
    var maximized: Boolean,
    var vSync: Boolean,
    var fov: Double = Defaults.FOV,
) {
    private var handle: Long
    private var projectionMatrix: Matrix4f = Matrix4f()

    init {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW")
        }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GL11.GL_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_TRUE)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3)
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE)
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE)

        GLFW.glfwWindowHint(GLFW.GLFW_MAXIMIZED, if (maximized) GLFW.GLFW_TRUE else GLFW.GLFW_FALSE)

        handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
        if (handle == MemoryUtil.NULL) {
            throw IllegalStateException("Failed to create the GLFW window")
        }

        GLFW.glfwSetFramebufferSizeCallback(handle) { handle: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            GL11.glViewport(0, 0, width, height)
        }

        GLFW.glfwMakeContextCurrent(handle)

        if (vSync) {
            GLFW.glfwSwapInterval(1)
        } else {
            GLFW.glfwSwapInterval(0)
        }

        GLFW.glfwShowWindow(handle)

        GL.createCapabilities();

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_STENCIL_TEST)
        GL11.glEnable(GL11.GL_CULL_FACE)
        GL11.glCullFace(GL11.GL_BACK)
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
        return projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, Constants.Z_NEAR, Constants.Z_FAR)
    }
}