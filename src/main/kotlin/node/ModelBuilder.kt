package me.johanrong.glare.node

import me.johanrong.glare.node.component.model.MeshComponent
import me.johanrong.glare.node.component.model.TextureComponent
import me.johanrong.glare.node.component.model.ShaderComponent
import me.johanrong.glare.type.DoubleString

class ModelBuilder : NodeBuilder() {
    var mesh: String? = null
    var texture: String? = null
    var shader: DoubleString? = null

    override fun build(): Node {
        if (parent == null && name != "Root") {
            throw Exception("Cannot build a node from a null parent")
        }

        val node = Node(name, transform, parent, children, components, script)

        mesh?.let {
            node.addComponent(MeshComponent(it))
        }

        texture?.let {
            node.addComponent(TextureComponent(it))
        }

        shader?.let {
            node.addComponent(ShaderComponent(it))
        }

        return node
    }

    companion object {
        fun go(block: ModelBuilder.() -> Unit): Node {
            val builder = ModelBuilder()
            builder.block()
            return builder.build()
        }
    }
}