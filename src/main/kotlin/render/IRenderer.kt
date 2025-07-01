package me.johanrong.glare.render

import me.johanrong.glare.node.base.Camera

interface IRenderer {
    fun render()
    fun cleanup()
}