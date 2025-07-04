package me.johanrong.glare.util

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.Window
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.type.io.MouseButton
import org.joml.Vector2d
import org.lwjgl.glfw.GLFW

class Input (val engine: GlareEngine) {
    var lastMousePosition: Vector2d = Vector2d()

    init {
        lastMousePosition = getMousePosition()
    }

    fun isKeyHeld(key: Keycode): Boolean {
        return GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_PRESS
    }

    fun isKeyReleased(key: Keycode): Boolean {
        return GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_RELEASE
    }

    fun isKeyPressed(key: Keycode): Boolean {
        println("Not implemented yet")
        return false
    }

    fun isMouseButtonPressed(button: MouseButton): Boolean {
        return GLFW.glfwGetMouseButton(engine.window.getHandle(), button.code) == GLFW.GLFW_PRESS
    }

    fun getMousePosition(): Vector2d {
        val posX = DoubleArray(1)
        val posY = DoubleArray(1)
        GLFW.glfwGetCursorPos(engine.window.getHandle(), posX, posY)
        return Vector2d(posX[0], posY[0])
    }

    fun getMouseDelta(): Vector2d {
        val currentPos = getMousePosition()
        val deltaX = currentPos.x - lastMousePosition.x
        val deltaY = currentPos.y - lastMousePosition.y
        lastMousePosition.set(currentPos)
        return Vector2d(deltaX, deltaY)
    }
}
