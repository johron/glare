package me.johanrong.glare.engine.node

import me.johanrong.glare.engine.common.Transform
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.component.CCategory
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent
import me.johanrong.glare.engine.node.component.core.ScriptsComponent

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

        if (!hasComponent(Component.SCRIPTS) && parent != null) {
            addComponent(ScriptsComponent())
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
        validateComponents(this)
    }

    fun removeComponent(component: IComponent) {
        if (!hasComponent(component.type)) {
            throw Exception("Component of type ${component.type} does not exist in this node.")
        }
        components.remove(component)
        validateComponents(this)
    }

    fun getComponent(type: Component): IComponent? {
        return components.firstOrNull { it.type == type }
    }

    fun getComponentsFromCategory(category: CCategory): Array<IComponent> {
        return components.filter { it.type.category == category }.toTypedArray()
    }

    fun hasComponent(type: Component): Boolean {
        return components.any { it.type == type }
    }

    fun hasComponentsFromCategory(category: CCategory): Boolean {
        return components.any { it.type.category == category }
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
            val node = builder.build()
            validateComponents(node)
            return node
        }

        fun validateComponents(node: Node) {
            val colliderComponents = node.components.filter {
                it.type.name.contains("COLLIDER")
            }

            if (colliderComponents.size > 1) {
                throw Exception("Node '${node.name}' cannot have more than one collider component")
            }

            if (colliderComponents.isNotEmpty() && !node.hasComponentsFromCategory(CCategory.BODY)) {
                throw Exception("Node '${node.name}' has a collider component but no body component. " +
                        "Please add a RigidbodyComponent to the node.")
            }
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