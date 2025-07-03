package me.johanrong.glare.node

import me.johanrong.glare.type.node.Mesh
import me.johanrong.glare.type.Transform

class MeshNode (name: String, parent: Node, private val mesh: Mesh, transform: Transform) : Node (name, parent, transform) {
    constructor(name: String, parent: Node, mesh: Mesh): this(name, parent, mesh, Transform())

   fun getMesh(): Mesh {
        return mesh
    }
}