package me.johanrong.glare.type

class Mesh (private val id: Int, private val vertexCount: Int, private val material: Material) {
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