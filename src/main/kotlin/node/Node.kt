package me.johanrong.glare.node

import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.type.Transform

open class Node (
    var name: String = "Node",
    var transform: Transform = Transform(),
    private var parent: Node? = null,
    private var children: MutableList<Node> = mutableListOf(),
    private var components: MutableList<IComponent> = mutableListOf(),
    private var script: IScript? = null
) {
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
        if (hasComponent(component.type)) {
            throw Exception("Component of type ${component.type} already exists in this node.")
        }
        components.add(component)
    }

    fun removeComponent(component: IComponent) {
        if (!hasComponent(component.type)) {
            throw Exception("Component of type ${component.type} does not exist in this node.")
        }
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