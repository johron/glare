package me.johanrong.glare.render

import me.johanrong.glare.core.GlareEngine

class MeshRenderer (val engine: GlareEngine) : IRenderer {
    override fun render() {
        if (engine.camera == null) {
            return
        }


        TODO("Not yet implemented")
    }

    override fun cleanup() {
        TODO("Not yet implemented")
    }

}