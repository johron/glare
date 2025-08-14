package me.johanrong.glare.core

import me.johanrong.glare.node.Node

class Physics(engine: Engine) {
    var physicsNodes = mutableListOf<Node>()

    fun update() {
        for (node in physicsNodes) {

        }
    }
}