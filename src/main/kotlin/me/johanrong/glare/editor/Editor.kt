package me.johanrong.glare.editor

import me.johanrong.glare.editor.ui.panel.ExplorerPanel
import me.johanrong.glare.editor.ui.panel.PropertiesPanel
import me.johanrong.glare.engine.component.core.CameraComponent
import me.johanrong.glare.engine.component.core.ScriptsComponent
import me.johanrong.glare.engine.component.graphics.MaterialComponent
import me.johanrong.glare.engine.component.graphics.MeshComponent
import me.johanrong.glare.engine.component.graphics.ShaderComponent
import me.johanrong.glare.engine.component.graphics.TextureComponent
import me.johanrong.glare.engine.component.lighting.PointLightComponent
import me.johanrong.glare.engine.component.physics.RigidbodyComponent
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.EngineConfig
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.io.Keycode
import me.johanrong.glare.engine.scripting.Script
import me.johanrong.glare.engine.type.Euler
import me.johanrong.glare.engine.type.Transform
import org.joml.Vector3d
import org.joml.Vector3f

fun main() {
    val config = EngineConfig(
        title = "Glare Editor",
        windowWidth = 1280,
        windowHeight = 720,
        maximized = false,
        vSync = false,
        fov = 70.0f,
        disableScripts = false
    )

    Engine(config, Editor())
}

class Editor : Script() {
    var tex: TextureComponent? = null

    override fun init() {
        val camera = Node.builder {
            name = "Camera"
            transform = Transform(Vector3d(0.0, 5.0, 5.0), Euler(0.0, 0.0, -90.0))
            parent = node
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(FreecamScript()))
            )
        }

        engine.setCamera(camera)

        Node.builder {
            name = "Node1"
            parent = camera
            components = mutableListOf(
                RigidbodyComponent()
            )
        }


        Node.builder {
            name = "Node2"
            parent = node
        }

        Node.builder {
            name = "Sun"
            transform = Transform(50.0, 100.0, 0.0)
            this.parent = node
            components = mutableListOf(
                PointLightComponent(intensity = 1000f),
            )
        }

        tex = TextureComponent().apply {
            path = "/texture/blue.png"
            loadTexture()
        }

        Node.builder {
            name = "Node"
            transform = Transform(Vector3d(0.0, 0.0, 0.0), Euler(0.0), Vector3f(5.0f, 0.5f, 5.0f))
            this.parent = node
            components = mutableListOf(
                MeshComponent("/model/cube.obj"),
                tex!!,
                ShaderComponent.Builder()
                    .vertex("/shader/mesh.vert")
                    .fragment("/shader/mesh.frag")
                    .build(),
                MaterialComponent(),
            )
        }

        engine.panels.add(ExplorerPanel())
        engine.panels.add(PropertiesPanel())
    }

    override fun update(delta: Double) {
        if (input.hasPressedKey(Keycode.G)) {
            println("Reloading texture")
            tex?.loadTexture()
        }
    }

    override fun render() {}
    override fun cleanup() {}
}