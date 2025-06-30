package me.johanrong.glare.node

import me.johanrong.glare.core.INodeScript
import me.johanrong.glare.util.Input

class Freecam : Node ("Freecam", FreecamScript())

class FreecamScript : INodeScript {


    override fun init() {}

    override fun update(delta: Double) {
        Input.
    }

    override fun render() {
        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}