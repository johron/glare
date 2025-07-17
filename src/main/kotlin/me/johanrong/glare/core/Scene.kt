package me.johanrong.glare.core

class Scene {
    private val nodes: MutableList<Node> = mutableListOf()

    fun addNode(node: Node) {
        nodes.add(node)
    }

    fun removeNode(node: Node) {
        nodes.remove(node)
    }

    fun getNodes(): List<Node> {
        return nodes
    }

    fun update(delta: Double) {
        for (node in nodes) {
            node.update(delta)
        }
    }
}