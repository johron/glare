package me.johanrong.glare.node

import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Transform

class NodeBuilder {
    var name: String = "Node"
    var transform: Transform = Transform()
    var parent: Node? = null
    var children: MutableList<Node> = mutableListOf()
    var components: MutableList<IComponent> = mutableListOf()
    var script: IScript? = null

    // fun name(value: String) = apply { name = value }
    // fun transform(value: Transform) = apply { transform = value }
    // fun parent(value: Node) = apply { parent = value }
    // fun children(value: MutableList<Node>) = apply { children = value }
    // fun components(value: MutableList<IComponent>) = apply { components = value }
    // fun script(value: IScript) = apply { script = value }

    fun build() : Node {
        if (parent == null) {
            throw Exception("Cannot build a node from a null parent")
        }

        return Node(name, transform, parent, children, components, script)
    }

    companion object {
        fun go(block: NodeBuilder.() -> Unit) : Node {
            val builder = NodeBuilder()
            builder.block()
            return builder.build()
        }
    }
}
