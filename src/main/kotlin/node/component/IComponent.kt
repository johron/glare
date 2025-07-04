package me.johanrong.glare.node.component

interface IComponent {
    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }
}