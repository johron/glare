package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.node.Component
import me.johanrong.glare.type.node.Mesh

class MeshComponent(var mesh: Mesh) : IComponent {
    override val type = Component.MESH
}