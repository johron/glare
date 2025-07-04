package me.johanrong.glare.node

import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.Transform

open class Node (
    var name: String,
    var transform: Transform,
    private var parent: Node?,
    private var children: MutableList<Node> = mutableListOf(),
    private var components: MutableList<IComponent> = mutableListOf(),
    private var script: IScript? = null
) {
    constructor(name: String, parent: Node?): this(
        name,
        Transform(),
        parent,
    )

    constructor(name: String, parent: Node?, components: MutableList<IComponent>): this(
        name,
        Transform(),
        parent,
        components = components
    )

    constructor(name: String, parent: Node?, transform: Transform): this(
        name,
        transform,
        parent,
    )

    constructor(name: String, parent: Node, script: IScript): this(
        name,
        Transform(),
        parent,
        script = script
    )

    constructor(name: String, parent: Node, script: IScript, transform: Transform): this(
        name,
        transform,
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

    fun getFirstChild(name: String): Node? {
        return children.firstOrNull { it.name == name }
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

    fun addComponent(component: IComponent) {
        components.add(component)
    }

    fun removeComponent(component: IComponent) {
        components.remove(component)
    }

    fun getComponent(type: Component): IComponent? {
        return components.firstOrNull { it.type == type }
    }

    fun hasComponent(type: Component): Boolean {
        return components.any { it.type == type }
    }

    fun getComponents(): List<IComponent> {
        return components
    }
}