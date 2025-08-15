package me.johanrong.glare.engine.io

import me.johanrong.glare.engine.core.Engine
import org.joml.Vector2d
import org.lwjgl.glfw.GLFW

class Input(val engine: Engine) {
    var lastMousePosition: Vector2d = Vector2d()

    private val pressedKeys = mutableSetOf<Keycode>()

    fun isKeyHeld(key: Keycode): Boolean {
        return GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_PRESS
    }

    fun isKeyReleased(key: Keycode): Boolean {
        return GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_RELEASE
    }

    fun hasPressedKey(key: Keycode): Boolean {
        val isPressed = GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_PRESS
        val isReleased = GLFW.glfwGetKey(engine.window.getHandle(), key.code) == GLFW.GLFW_RELEASE

        if (isPressed) {
            pressedKeys.add(key)
        } else if (isReleased && pressedKeys.contains(key)) {
            pressedKeys.remove(key)
            return true
        }
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