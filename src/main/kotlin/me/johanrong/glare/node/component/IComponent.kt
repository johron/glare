package me.johanrong.glare.node.component

interface IComponent {
    val type: Component

    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }

    fun cleanup()
}