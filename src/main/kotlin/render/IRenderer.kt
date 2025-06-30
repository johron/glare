package me.johanrong.glare.render

import me.johanrong.glare.node.ICamera

interface IRenderer {
    fun render(camera: ICamera)
}