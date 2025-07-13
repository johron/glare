package me.johanrong.glare.node

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Transform

open class NodeBuilder {
    var name: String = "Node"
    var transform: Transform = Transform()
    var parent: Node? = null
    var children: MutableList<Node> = mutableListOf()
    var components: MutableList<IComponent> = mutableListOf()

    //fun name(value: String) = apply { name = value }
    //fun transform(value: Transform) = apply { transform = value }
    //fun parent(value: Node) = apply { parent = value }
    //fun child(value: Node) = apply { children.add(value)}
    //fun component(value: IComponent) = apply { components.add(value) }
    //fun components(value: MutableList<IComponent>) = apply { components.addAll(value) }
    //fun children(value: MutableList<Node>) = apply { children.addAll(value) }
    //fun script(value: IScript) = apply { script = value }

    open fun build() : Node {
        if (parent == null && name != "Root") {
            throw Exception("Cannot build a node from a null parent")
        }

        return Node(name, transform, parent, children, components)
    }

    companion object {
        fun go(block: NodeBuilder.() -> Unit) : Node {
            val builder = NodeBuilder()
            builder.block()
            return builder.build()
        }
    }
}
