import me.johanrong.glare.core.Engine
import me.johanrong.glare.core.graphics.OpenGL
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.core.CameraComponent
import me.johanrong.glare.node.component.core.IScript
import me.johanrong.glare.node.component.core.ScriptsComponent
import me.johanrong.glare.node.component.graphics.MeshComponent
import me.johanrong.glare.node.component.graphics.ShaderComponent
import me.johanrong.glare.node.component.graphics.TextureComponent
import me.johanrong.glare.node.component.Component
import me.johanrong.glare.common.Euler
import me.johanrong.glare.common.Text
import me.johanrong.glare.common.Transform
import me.johanrong.glare.io.Keycode
import me.johanrong.glare.io.Input
import me.johanrong.glare.node.component.graphics.MaterialComponent
import me.johanrong.glare.node.component.lighting.PointLightComponent
import me.johanrong.glare.node.component.physics.RigidbodyComponent
import org.joml.Vector3d
import org.joml.Vector3f

fun main() {
    val window = Window(
        "Glare GE",
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
            name = "Freecam"
            transform = Transform(Vector3d(0.0, 5.0, 5.0), Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript())),
            )
        }

        engine.setCamera(camera)

        Node.builder {
            name = "Sun"
            transform = Transform(50.0, 100.0, 0.0)
            parent = root
            components = mutableListOf(
                PointLightComponent(intensity = 1000f),
            )
        }

        Node.builder {
            name = "Node"
            transform = Transform(Vector3d(0.0, 4.0, 0.0), Euler(0.0), Vector3f(0.25f))
            parent = root
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                TextureComponent("/texture/map.png"),
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build(),
                MaterialComponent(),
                RigidbodyComponent(),
            )
        }

        Node.builder {
            name = "Node"
            transform = Transform(Vector3d(0.0, 0.0, 0.0), Euler(0.0), Vector3f(5.0f, 0.5f, 5.0f))
            parent = root
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                TextureComponent("/texture/blue.png"),
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build(),
                MaterialComponent(),
            )
        }
    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}