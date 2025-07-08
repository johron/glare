package me.johanrong.glare.node.component.mesh

import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component
import me.johanrong.glare.util.Mesh

open class ObjComponent(path: String) : IComponent {
    override val type = Component.MESH

    private val id: Int = Mesh.createVAO()
    private val vertexCount: Int

    init {
        val inputStream = object {}.javaClass.getResourceAsStream(path) ?: throw Exception("Resource not found: $path")
        val obj = ObjUtils.convertToRenderable(ObjReader.read(inputStream))
        inputStream.close()

        val indices = ObjData.getFaceVertexIndicesArray(obj)
        val vertices = ObjData.getVerticesArray(obj)
        val texCoords = ObjData.getTexCoordsArray(obj, 2)

        Mesh.storeIndiciesBuffer(Mesh.storeDataArrayInBuffer(indices))
        Mesh.storeDataInAttribList(0, 3, Mesh.storeDataArrayInBuffer(vertices))
        if (!texCoords.isEmpty()) {
            Mesh.storeDataInAttribList(1, 2, Mesh.storeDataArrayInBuffer(texCoords))
        }
        Mesh.unbind()

        vertexCount = indices.size
    }

    fun getId(): Int {
        return id
    }

    fun getVertexCount(): Int {
        return vertexCount
    }
}