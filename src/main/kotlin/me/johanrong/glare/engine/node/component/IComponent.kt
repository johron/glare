package me.johanrong.glare.engine.node.component

import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.util.log
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

interface IComponent {
    val type: Component
    var node: Node?

    fun getComponentName(): String {
        return this::class.simpleName ?: throw Exception("Could not retrieve component name")
    }

    fun onAttach(node: Node) {
        this.node = node
    }

    fun apply() {
        log("${getComponentName()} is not applicable for this function")
    }

    fun cleanup() {}

    fun getExportProperties(): List<ExportPropertyInfo> {
        val result = mutableListOf<ExportPropertyInfo>()

        this::class.memberProperties.forEach { property ->
            if (property.findAnnotation<ExportProperty>() != null) {
                property.isAccessible = true
                val value = property.getter.call(this)

                result.add(ExportPropertyInfo(
                    name = property.name,
                    value = value,
                    property = property,
                    mutable = property.findAnnotation<ExportProperty>()?.mutable ?: true
                ))
            }
        }

        return result
    }
}