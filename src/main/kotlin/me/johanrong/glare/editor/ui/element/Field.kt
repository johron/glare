package me.johanrong.glare.editor.ui.element

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.component.ExportPropertyInfo
import me.johanrong.glare.engine.ui.IPanel
import org.joml.Vector3f
import kotlin.reflect.full.createType

class Field(property: ExportPropertyInfo) : IPanel {
    override var name: String = ""
    override var engine: Engine? = null

    //fun intField(name: String, )

    init {
        val type = property.property.returnType

        when (type) {
            Int::class.createType() -> {
                text(property.name)
                sameLine()
                inputText(property.name, property.value.toString()) { newValue ->
                    println(newValue)
                }
            }
            Float::class.createType() -> {
                //text(property.name)
                //sameLine()
                inputText(property.name, property.value.toString()) { newValue ->
                    println(newValue)
                }
            }
            String::class.createType() -> {
            }
            Boolean::class.createType() -> {
            }
            Vector3f::class.createType() -> {

            }
            else -> {
                // Handle other types or unsupported types
                // Example: text("Unsupported type for ${property.name}")
            }
        }
    }
}