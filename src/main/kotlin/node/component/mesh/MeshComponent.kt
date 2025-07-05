package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component

class MeshComponent(private val id: Int, private val vertexCount: Int) : IComponent {
    override val type = Component.MESH

    fun getId(): Int {
        return id
    }

    fun getVertexCount(): Int {
        return vertexCount
    }
}