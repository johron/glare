package me.johanrong.glare.render

import me.johanrong.glare.node.Camera

interface IRenderer {
    fun render(camera: Camera)
}