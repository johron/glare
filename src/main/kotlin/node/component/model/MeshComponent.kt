package me.johanrong.glare.node.component.model

import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.util.Mesh

open class MeshComponent(isPrimary: Boolean = true) : IComponent {
    override val type = Component.MESH

    private val id: Int = Mesh.createVAO()

    private var indices: IntArray? = null
    private var vertices: FloatArray? = null
    private var texCoords: FloatArray? = null

    constructor(indices: IntArray, vertices: FloatArray, texCoords: FloatArray) : this(false) {
        this.indices = indices
        this.vertices = vertices
        this.texCoords = texCoords

        load()
    }

    constructor(path: String) : this(false) {
        val inputStream = object {}.javaClass.getResourceAsStream(path) ?: throw Exception("Resource not found: $path")
        val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))
        inputStream.close()

        this.indices = ObjData.getFaceVertexIndicesArray(obj)
        this.vertices = ObjData.getVerticesArray(obj)
        this.texCoords = ObjData.getTexCoordsArray(obj, 2)

        load()
    }

    init {
        if (isPrimary) {
            throw Exception("Cannot use the primary constructor")
        }
    }

    private fun load() {
        Mesh.storeIndiciesBuffer(Mesh.storeDataArrayInBuffer(indices))
        Mesh.storeDataInAttribList(0, 3, Mesh.storeDataArrayInBuffer(vertices))
        if (!texCoords!!.isEmpty()) {
            Mesh.storeDataInAttribList(1, 2, Mesh.storeDataArrayInBuffer(texCoords))
        }
        Mesh.unbind()
    }

    fun getId(): Int {
        return id
    }

    fun getVertexCount(): Int {
        return indices?.size ?: throw Exception("Indices not set for MeshComponent")
    }
}