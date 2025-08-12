package me.johanrong.glare.node.component.graphics

import me.johanrong.glare.common.Color
import me.johanrong.glare.common.Text
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.IComponent
import org.joml.Vector3f

/**
 * Component for displaying text that always faces the camera (billboard effect)
 */
class BillboardTextComponent(
    var text: Text,
    var scale: Float = 1.0f,
    var offset: Vector3f = Vector3f(0f, 0f, 0f),
    var visible: Boolean = true
) : IComponent {
    override val type = Component.BILLBOARD
    override fun cleanup() {
    }

    /**
     * Updates the displayed text
     */
    fun setText(newText: String) {
        text = text.copy(content = newText)
    }

    /**
     * Updates text color
     */
    fun setColor(newColor: Color) {
        text = text.copy(color = newColor)
    }

    /**
     * Updates text size
     */
    fun setSize(newSize: Float) {
        text = text.copy(size = newSize)
    }
}