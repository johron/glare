package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.node.Component
import me.johanrong.glare.type.node.Material
import me.johanrong.glare.type.node.Texture

class MeshComponent(private val id: Int, private val vertexCount: Int, private val material: Material) : IComponent {
    override val type = Component.MESH

    constructor(id: Int, vertexCount: Int) : this(id, vertexCount, Material())

    constructor(id: Int, vertexCount: Int, texture: Texture) : this(id, vertexCount, Material(texture))

    fun getId(): Int {
        return id
    }

    fun getVertexCount(): Int {
        return vertexCount
    }

    fun getMaterial(): Material {
        return material
    }
}