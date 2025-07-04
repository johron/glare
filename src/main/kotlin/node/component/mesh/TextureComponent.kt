package me.johanrong.glare.node.component.mesh

import me.johanrong.glare.node.component.IComponent
import me.johanrong.glare.type.Component

class TextureComponent(private var id: Int) : IComponent {
    override val type = Component.TEXTURE

    fun getId(): Int {
        return id
    }
}