import me.johanrong.glare.core.Engine
import me.johanrong.glare.core.Window
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.component.core.CameraComponent
import me.johanrong.glare.node.component.core.EngineRefComponent
import me.johanrong.glare.node.component.core.IScript
import me.johanrong.glare.node.component.core.ScriptsComponent
import me.johanrong.glare.node.component.model.MeshComponent
import me.johanrong.glare.node.component.model.ShaderComponent
import me.johanrong.glare.node.component.model.TextureComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.type.io.Keycode
import me.johanrong.glare.util.Input
import org.joml.Vector3f

fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = false,
        vSync = true,
    )

    Engine(window, TestGame())
}

class TestGame : IScript {
    companion object {
        lateinit var engine: Engine
    }

    lateinit var input: Input

    override fun init(root: Node) {
        engine = (root.getComponent(Component.ENGINE_REF) as EngineRefComponent).getEngine()
        input = Input(engine)

        val camera = Node.builder {
            name = "Freecam"
            transform = Transform(Euler(0.0, 0.0, -90.0))
            parent = root
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript()))
            )
        }

        engine.setCamera(camera)

        Node.builder {
            name = "Node"
            transform = Transform(0.0, 0.0, -5.0)
            parent = root
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                TextureComponent("texture/map.png"),
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build()
            )
        }
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)

        if (input.hasPressedKey(Keycode.G)) {
            val camera = engine.getCamera()
            Node.builder {
                name = "test"
                transform = Transform(camera!!.transform.clone().position, Vector3f(0.1f))
                parent = engine.root
                components = mutableListOf(
                    MeshComponent("/model/cube.obj"),
                    ShaderComponent.Builder()
                        .vertex("/shader/mesh.vert")
                        .fragment("/shader/mesh.frag")
                        .build()
                )
            }
        }
    }

    override fun render() {}
    override fun cleanup() {}
}