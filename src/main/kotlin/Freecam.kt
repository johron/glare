package me.johanrong.glare

import me.johanrong.glare.TestGame.Companion.engine
import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.base.Camera
import me.johanrong.glare.node.Node
import me.johanrong.glare.type.Keycode
import me.johanrong.glare.util.Input


class Freecam (name: String, parent: Node) : Camera(name, FreecamScript(), parent) {
    constructor (parent: Node) : this("Freecam", parent)
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
            //node.transform.translate(0.0, 0.0, 5.0 * delta)
            //println("Moving forward")
        }
    }

    override fun render() {
        //TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}