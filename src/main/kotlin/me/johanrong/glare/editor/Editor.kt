package me.johanrong.glare.editor

import me.johanrong.glare.editor.ui.panel.ExplorerPanel
import me.johanrong.glare.editor.ui.panel.PropertiesPanel
import me.johanrong.glare.engine.common.Euler
import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.EngineConfig
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.core.CameraComponent
import me.johanrong.glare.engine.node.component.core.IScript
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.node.component.physics.RigidbodyComponent
import org.joml.Vector3d

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

class Editor : IScript {
    override fun init(node: Node) {
        val engine: Engine = node.engine

        val camera = Node.builder {
            name = "Camera"
            transform = Transform(Vector3d(0.0, 5.0, 5.0), Euler(0.0, 0.0, -90.0))
            parent = node
            components = mutableListOf(
                CameraComponent(),
                ScriptsComponent(mutableListOf(
                    FreecamScript()
                ))
            )
        }

        engine.addNode(camera)
        engine.setCamera(camera)

        engine.addNode(Node.builder {
            name = "Node1"
            parent = camera
            components = mutableListOf(
                RigidbodyComponent()
            )
        })


        engine.addNode(Node.builder {
            name = "Node2"
            parent = node
        })

        engine.panels.add(ExplorerPanel())
        engine.panels.add(PropertiesPanel())
    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}