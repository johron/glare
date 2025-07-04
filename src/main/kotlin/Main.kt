package me.johanrong.glare

import me.johanrong.glare.core.GlareEngine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.core.Window
import me.johanrong.glare.core.IRootScript
import me.johanrong.glare.node.Node
import me.johanrong.glare.node.base.Freecam
import me.johanrong.glare.node.component.EngineRefComponent
import me.johanrong.glare.node.component.mesh.MeshComponent
import me.johanrong.glare.node.component.mesh.ShaderComponent
import me.johanrong.glare.type.Euler
import me.johanrong.glare.type.Transform
import me.johanrong.glare.util.Loader

fun main() {
    val window = Window(
        "Glare GE",
        1280,
        720,
        maximized = false,
        vSync = true,
    )

    GlareEngine(window, TestGame())
}

class TestGame : IRootScript {
    companion object {
        lateinit var engine: GlareEngine
    }

    override fun init(engine: GlareEngine) {
        TestGame.engine = engine
        engine.camera = Freecam(engine.root, Transform(Euler(0.0, 0.0, 0.0)))

        val node = Node("Node", engine.root, Transform(0.0, 0.0, -5.0))
        val shader = ShaderComponent("/shader/mesh.vert", "/shader/mesh.frag")
        val texture = Loader.loadTexture("texture/map.png")
        val mesh = Loader.loadObj("/model/cube.obj")
        node.addComponent(texture)
        node.addComponent(mesh)
        node.addComponent(shader)
    }

    override fun update(delta: Double) {
        val node = engine.root.getFirstChild("Node")
        node!!.transform.rotation.addYaw(100.0 * delta)
    }

    override fun render() {}
    override fun cleanup() {}
}