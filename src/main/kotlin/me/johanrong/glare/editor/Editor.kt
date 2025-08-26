package me.johanrong.glare.editor

import me.johanrong.glare.editor.ui.panel.ExplorerPanel
import me.johanrong.glare.editor.ui.panel.PropertiesPanel
import me.johanrong.glare.engine.component.core.CameraComponent
import me.johanrong.glare.engine.component.core.ScriptsComponent
import me.johanrong.glare.engine.component.physics.RigidbodyComponent
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.EngineConfig
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.scripting.Script
import me.johanrong.glare.engine.type.Euler
import me.johanrong.glare.engine.type.Transform
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

class Editor : Script() {
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

        engine.panels.add(ExplorerPanel())
        engine.panels.add(PropertiesPanel())
    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}