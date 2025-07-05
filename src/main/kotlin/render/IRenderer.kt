package me.johanrong.glare.render

import me.johanrong.glare.node.component.mesh.ShaderComponent

interface IRenderer {
    fun render()
    fun cleanup()
}