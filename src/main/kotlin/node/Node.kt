package me.johanrong.glare.node

import me.johanrong.glare.core.INodeScript
import me.johanrong.glare.type.Transform

open class Node (
    var name: String,
    var transform: Transform,
    private var parent: Node? = null,
    private var children: MutableList<Node> = mutableListOf(),
    private var script: INodeScript? = null
) {
    constructor(name: String): this(
        name,
        Transform()
    )

    constructor(name: String, script: INodeScript): this(
        name,
        Transform(),
        script = script
    )

    init {
        script?.init()
    }

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

    fun setParent(parent: Node) {
        this.parent = parent
        parent.addChild(this)
    }

    fun getParent(): Node? {
        return parent
    }

    fun getScript(): INodeScript? {
        return script
    }

    fun destroy() {
        script?.cleanup()
        parent?.removeChild(this)
        parent = null
        script = null
    }
}