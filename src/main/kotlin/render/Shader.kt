package me.johanrong.glare.render

import me.johanrong.glare.util.loadPlain

object Shader {
    const val VERTEX_HEADER = """
        #version 400
    """

    const val FRAGMENT_HEADER = """
        #version 400
    """

    fun makeVertex(path: String): String {
        var shader = loadPlain(path)
        shader = VERTEX_HEADER + shader
        return shader
    }

    fun makeFragment(path: String): String {
        var shader = loadPlain(path)
        shader = FRAGMENT_HEADER + shader
        return shader
    }
}