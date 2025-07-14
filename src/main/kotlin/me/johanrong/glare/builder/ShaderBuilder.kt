package me.johanrong.glare.builder

import me.johanrong.glare.node.component.model.ShaderComponent

class ShaderBuilder {
    var vertex: String? = null
    var fragment: String? = null
    var geometry: String? = null
    var control: String? = null
    var eval: String? = null
    var compute: String? = null

    fun build(): ShaderComponent {
        return ShaderComponent(
            vertexPath = vertex,
            fragmentPath = fragment,
            geometryPath = geometry,
            controlPath = control,
            evalPath = eval,
            computePath = compute
        )
    }

    companion object {
        fun go(block: ShaderBuilder.() -> Unit): ShaderComponent {
            val builder = ShaderBuilder()
            builder.block()
            return builder.build()
        }
    }
}