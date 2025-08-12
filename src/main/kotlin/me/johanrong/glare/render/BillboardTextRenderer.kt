package me.johanrong.glare.render

import me.johanrong.glare.common.Text
import me.johanrong.glare.core.Engine
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.node.component.core.CameraComponent
import me.johanrong.glare.node.component.graphics.BillboardTextComponent
import org.joml.Matrix4f
import org.joml.Vector3f

class BillboardTextRenderer(val engine: Engine) : IRenderer {
    override val priority: Int = 2
    override fun init() {
    }

    override fun cleanup() {
    }

    override fun render(node: Node) {
        val billboardComponent = node.getComponent(Component.BILLBOARD) as? BillboardTextComponent
            ?: return // Skip if no BillboardTextComponent

        if (!billboardComponent.visible) return

        val camera = engine.getCamera()!!

        // 1. Generate the billboard matrix to face camera
        val billboardMatrix = Matrix4f()
        calculateBillboardMatrix(billboardMatrix, camera, node.transform.getPosition())

        // 2. Apply scaling and offset
        billboardMatrix.translate(billboardComponent.offset)
        billboardMatrix.scale(billboardComponent.scale * billboardComponent.text.size)

        // 3. Render text with the calculated transform
        renderText(billboardComponent.text, billboardMatrix)
    }

    private fun calculateBillboardMatrix(result: Matrix4f, camera: Node, position: Vector3f) {
        // Extract camera right and up vectors, and use them to create a rotation
        // that makes the billboard face the camera
        val cameraRight = camera.transform.getRight()
        val cameraUp = camera.transform.getUp()

        result.identity()
        result.m00(cameraRight.x)
        result.m01(cameraUp.x)
        result.m10(cameraRight.y)
        result.m11(cameraUp.y)
        result.m20(cameraRight.z)
        result.m21(cameraUp.z)

        // Set billboard position
        result.m30(position.x)
        result.m31(position.y)
        result.m32(position.z)
    }

    private fun renderText(text: Text, transform: Matrix4f) {
        println("render text:")
    }
}