package test

import me.johanrong.glare.engine.common.Euler
import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.EngineConfig
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
import org.joml.Vector3d
import org.joml.Vector3f


fun main() {
    val config = EngineConfig(
        title = "Glare GE",
        windowWidth = 1280,
        windowHeight = 720,
        maximized = false,
        vSync = false,
        fov = 70.0f,
        iconPaths = listOf("me/johanrong/glare/assets/glare_icon.png"),
    )

    Engine(config, TestGame())
}

class TestGame : IScript {
    override fun init(parent: Node) {
        val engine: Engine = parent.engine

        val camera = Node.builder {
            name = "Freecam"
            transform = Transform(Vector3d(0.0, 5.0, 5.0), Euler(0.0, 0.0, -90.0))
            this.parent = parent
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript())),
            )
        }

        engine.setCamera(camera)

        Node.builder {
            name = "Sun"
            transform = Transform(50.0, 100.0, 0.0)
            this.parent = parent
            components = mutableListOf(
                PointLightComponent(intensity = 1000f),
            )
        }

        /*Node.builder {
            name = "Node"
            transform = Transform(Vector3d(0.0, 4.0, 0.0), Euler(0.0), Vector3f(1f))
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
                BoxColliderComponent(),
            )
        }*/

        Node.builder {
            name = "Node"
            transform = Transform(Vector3d(0.0, 0.0, 0.0), Euler(0.0), Vector3f(5.0f, 0.5f, 5.0f))
            this.parent = parent
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                TextureComponent("/texture/blue.png"),
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build(),
                MaterialComponent(),
                StaticbodyComponent(),
                BoxColliderComponent(Transform(Vector3d(0.0), Euler(0.0), Vector3f(5.0f, 0.5f, 5.0f))),
            )
        }
    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}