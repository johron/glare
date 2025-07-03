package me.johanrong.glare

import me.johanrong.glare.TestGame.Companion.engine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.base.Camera
import me.johanrong.glare.node.Node
import me.johanrong.glare.type.Keycode
import me.johanrong.glare.type.Transform
import me.johanrong.glare.util.Input


class Freecam (name: String, parent: Node, transform: Transform) : Camera(name, FreecamScript(), parent, transform) {
    constructor (parent: Node) : this("Freecam", parent, Transform())
    constructor (parent: Node, transform: Transform) : this("Freecam", parent, transform)
}

class FreecamScript : IScript {
    lateinit var node: Node

    var input = Input(engine)

    override fun init(node: Node) {
        this.node = node
        println("${engine.window.getHandle()}")
    }

    override fun update(delta: Double) {
        if (input.isKeyHeld(Keycode.W)) {
            node.transform.translate(0.0, 0.0, 5.0 * delta)
        }
        if (input.isKeyHeld(Keycode.S)) {
            node.transform.translate(0.0, 0.0, -5.0 * delta)
        }
        if (input.isKeyHeld(Keycode.A)) {
            node.transform.translate(-5.0 * delta, 0.0, 0.0)
        }
        if (input.isKeyHeld(Keycode.D)) {
            node.transform.translate(5.0 * delta, 0.0, 0.0)
        }

        if (input.isKeyHeld(Keycode.SPACE)) {
            node.transform.translate(0.0, 5.0 * delta, 0.0)
        }
        if (input.isKeyHeld(Keycode.SHIFT)) {
            node.transform.translate(0.0, -5.0 * delta, 0.0)
        }
    }

    override fun render() {
        //TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}