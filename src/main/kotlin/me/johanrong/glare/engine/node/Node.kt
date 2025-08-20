package me.johanrong.glare.engine.node

import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.node.component.lighting.LightComponent

open class Node (
    var name: String = "Node",
    var transform: Transform = Transform(),
    private var parent: Node? = null,
    private var children: MutableList<Node> = mutableListOf(),
    private var components: MutableList<IComponent> = mutableListOf(),
) {
    lateinit var engine: Engine

    init {
        if (parent != null) {
            engine = parent!!.engine
        }

        for (component in components) {
            component.onAttach(this)
        }

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

    fun destroy() {
        components.forEach { it.cleanup() }
        val childrenCopy = ArrayList(children)
        for (child in childrenCopy) {
            child.destroy()
        }
        parent?.removeChild(this)
        parent = null
    }

    fun update(delta: Double) {
        (getComponent(Component.SCRIPTS) as? ScriptsComponent)?.scripts?.forEach { script ->
            if ()
            script.update(delta)
        }

        for (child in children) {
            child.update(delta)
        }
    }

    fun fixedUpdate() {
        (getComponent(Component.SCRIPTS) as? ScriptsComponent)?.scripts?.forEach { script ->
            script.fixedUpdate()
        }

        for (child in children) {
            child.fixedUpdate()
        }
    }

    fun addComponent(component: IComponent) {
        component.onAttach(this)
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

    fun getGlobalTransform(): Transform {
        return if (parent == null) {
            transform
        } else {
            parent!!.getGlobalTransform().combine(transform)
        }
    }

    companion object {
        fun builder(block: Builder.() -> Unit) : Node {
            val builder = Builder()
            builder.block()
            return builder.build()
        }
    }

    class Builder {
        var name: String = "Node"
        var transform: Transform = Transform()
        var parent: Node? = null
        var children: MutableList<Node> = mutableListOf()
        var components: MutableList<IComponent> = mutableListOf()

        fun name(value: String) = apply { name = value }
        fun transform(value: Transform) = apply { transform = value }
        fun parent(value: Node?) = apply { parent = value }
        fun addChild(child: Node) = apply { children.add(child) }
        fun addComponent(component: IComponent) = apply { components.add(component) }

        fun build() : Node {
            if (parent == null && name != "Root") {
                throw Exception("Cannot build a node from a null parent")
            }

            return Node(name, transform, parent, children, components)
        }
    }
}