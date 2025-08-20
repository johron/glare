package editor

import me.johanrong.glare.engine.common.Euler
import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.Window
import me.johanrong.glare.engine.core.graphics.OpenGL
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.core.CameraComponent
import me.johanrong.glare.engine.node.component.core.IScript
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.node.component.graphics.MaterialComponent
import me.johanrong.glare.engine.node.component.graphics.MeshComponent
import me.johanrong.glare.engine.node.component.graphics.ShaderComponent
import me.johanrong.glare.engine.node.component.graphics.TextureComponent
import me.johanrong.glare.engine.node.component.lighting.PointLightComponent
import me.johanrong.glare.engine.node.component.physics.StaticbodyComponent
import me.johanrong.glare.engine.node.component.physics.collision.BoxColliderComponent
import me.johanrong.glare.engine.ui.ExplorerPanel
import org.joml.Vector3d
import org.joml.Vector3f

fun main() {
    val window = Window(
        "Glare Editor",
        1280,
        720,
        maximized = false,
        vSync = false,
    )

    Engine(window, OpenGL(), TestGame())
}

class TestGame : IScript {
    override fun init(root: Node) {
        val engine: Engine = root.engine

        val camera = Node.builder {
            name = "Camera"
            transform = Transform(Vector3d(0.0, 5.0, 5.0), Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(
                CameraComponent(),
            )
        }

        engine.setCamera(camera)

    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}