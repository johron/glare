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

    fun build() : Node {
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
