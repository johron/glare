package me.johanrong.glare.node

import me.johanrong.glare.type.Transform

class Node (
    var name: String,
    var transform: Transform,
    var parent: Node? = null,
    var children: MutableList<Node> = mutableListOf(),
) {
    constructor(name: String): this(
        name,
        Transform()
    )

    fun addChild(child: Node) {
        child.parent = this
        children.add(child)
    }

    fun removeChild(child: Node) {
        child.parent = null
        children.remove(child)
    }

    fun getChildren(): List<Node> {
        return children
    }
}