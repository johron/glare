package me.johanrong.glare.editor.ui.field

import me.johanrong.glare.engine.node.component.ExportedProperty
import me.johanrong.glare.engine.ui.IImGuiWrapper
import org.joml.Vector3f
import kotlin.reflect.full.createType

class Field(property: ExportedProperty) : IImGuiWrapper {
    init {
        val type = property.type

        when (type) {
            Int::class.createType() -> {
                text(property.name)
                sameLine()
                inputInt(" ", property.get() as Int) { new ->
                    println(new)
                    property.set(new)
                }
            }
            Float::class.createType() -> {
                text(property.name)
                sameLine()
                inputFloat(" ", property.get() as Float) { new ->
                    println(new)
                    property.set(new)
                }
            }
            String::class.createType() -> {
                text(property.name)
                sameLine()
                inputText("", property.get() as String) { new ->
                    println("New String: $new")
                    property.set(new)
                }
            }
            Boolean::class.createType() -> {
                text(property.name)
                sameLine()
                checkbox("", property.get() as Boolean) { new ->
                    println("New Boolean: $new")
                    property.set(new)
                }
            }
            Vector3f::class.createType() -> {
                text(property.name)
                sameLine()
                inputVector3f(" ", property.get() as Vector3f) { new ->
                    println("New Vector3f: $new")
                    property.set(new)
                }
            }
            else -> {
                // Handle other types or unsupported types
                // Example: text("Unsupported type for ${property.name}")
            }
        }
    }
}