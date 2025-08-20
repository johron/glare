package me.johanrong.glare.editor

import me.johanrong.glare.engine.common.Euler
import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.Window
import me.johanrong.glare.engine.core.OpenGL
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.core.CameraComponent
import me.johanrong.glare.engine.node.component.core.IScript
import me.johanrong.glare.editor.ui.ExplorerPanel
import me.johanrong.glare.engine.core.EngineConfig
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import org.joml.Vector3d

fun main() {
    val config = EngineConfig(
        title = "Glare Editor",
        windowWidth = 1280,
        windowHeight = 720,
        maximized = false,
        vSync = false,
        fov = 70.0f,
        disableScripts = true
    )

    Engine(config, Editor())
}

class Editor : IScript {
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

        engine.panels.add(ExplorerPanel())

    }

    override fun update(delta: Double) {
    }

    override fun render() {}
    override fun cleanup() {}
}