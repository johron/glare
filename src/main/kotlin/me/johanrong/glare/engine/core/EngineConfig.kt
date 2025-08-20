package me.johanrong.glare.engine.core

import me.johanrong.glare.engine.core.graphics.IGraphics
import me.johanrong.glare.engine.core.graphics.OpenGL

data class EngineConfig(
    var title: String = "Glare",
    var windowWidth: Int = 1280,
    var windowHeight: Int = 720,
    var maximized: Boolean = false,
    var vSync: Boolean = true,
    var fov: Float = 70.0f,
    var iconPaths: List<String> = listOf("me/johanrong/glare/assets/glare_icon.png"),

    val graphics: IGraphics = OpenGL()
)
