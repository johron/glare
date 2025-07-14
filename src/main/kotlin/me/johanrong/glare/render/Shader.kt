package me.johanrong.glare.render

import me.johanrong.glare.util.loadPlain

object Shader {
    const val VERSION = "#version 460"

    const val VERTEX_HEADER = """
    """

    const val FRAGMENT_HEADER = """
    """

    const val GEOMETRY_HEADER = """
    """

    const val CONTROL_HEADER = """
    """

    const val EVAL_HEADER = """
    """

    fun makeVertex(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + VERTEX_HEADER + shader
        return shader
    }

    fun makeFragment(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + FRAGMENT_HEADER + shader
        return shader
    }

    fun makeGeometry(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + GEOMETRY_HEADER + shader
        return shader
    }

    fun makeControl(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + CONTROL_HEADER + shader
        return shader
    }

    fun makeEval(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + EVAL_HEADER + shader
        return shader
    }

    fun makeCompute(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + shader
        return shader
    }
}