package me.johanrong.glare.node

import me.johanrong.glare.core.IScript
import me.johanrong.glare.type.Transform

open class Node (
    var name: String,
    var transform: Transform,
    private var parent: Node?,
    private var children: MutableList<Node> = mutableListOf(),
    private var script: IScript? = null
) {
    constructor(name: String, parent: Node?): this(
        name,
        Transform(),
        parent,
    )

    constructor(name: String, script: IScript, parent: Node): this(
        name,
        Transform(),
        parent,
        script = script
    )

    init {
        script?.init(this)
        parent?.addChild(this)
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

    fun getScript(): IScript? {
        return script
    }

    fun destroy() {
        script?.cleanup()
        parent?.removeChild(this)
        parent = null
        script = null
    }

    fun update(delta: Double) {
        script?.update(delta)
        for (child in children) {
            child.update(delta)
        }
    }
}