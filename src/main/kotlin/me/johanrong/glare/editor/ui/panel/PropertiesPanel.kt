package me.johanrong.glare.editor.ui.panel

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.event.EventBus
import me.johanrong.glare.engine.event.NodeSelectedEvent
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.IComponent
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.ui.IPanel
import me.johanrong.glare.engine.util.getExportProperties

class PropertiesPanel : IPanel {
    override var name: String = "Properties"
    override var engine: Engine? = null

    private var node: Node? = null
    private var selectedAdditionComponent = 0

    init {
        EventBus.subscribe<NodeSelectedEvent> { event ->
            node = event.node
        }
    }

    override fun render() {
        if (node == null) {
            text("No node selected")
            return
        }

        text(node!!.name)
        separator()
        treeNodeEx("Transform", 32) {
            val transform = node!!.transform
            treeNodeEx("Position", 32) {
                text("X: ${transform.position.x}")
                sameLine()
                text("Y: ${transform.position.y}")
                sameLine()
                text("Z: ${transform.position.z}")
            }

            treeNodeEx("Rotation", 32) {
                text("X: ${transform.rotation.getRoll()}")
                sameLine()
                text("Y: ${transform.rotation.getPitch()}")
                sameLine()
                text("Z: ${transform.rotation.getYaw()}")
            }

            treeNodeEx("Scale", 32) {
                text("X: ${transform.scale.x}")
                sameLine()
                text("Y: ${transform.scale.y}")
                sameLine()
                text("Z: ${transform.scale.z}")
            }
        }

        treeNodeEx("Components", 32) {
            val toRemove = mutableListOf<IComponent>()
            for (component in node!!.getComponents()) {
                if (component is ScriptsComponent) continue

                treeNode(component.getComponentName()) {
                    val properties = component.getExportProperties()
                    for (property in properties) {
                        text("${property.name}: ${property.value}")
                        val type = property.property.returnType
                        text(type.toString())
                        if (property.mutable) {
                            sameLine()
                            button("Edit")
                        }
                    }
                    button("Remove") {
                        println("Removing component: ${component.getComponentName()} from node: ${node!!.name}")
                        toRemove.add(component)
                    }
                }
            }

            for (component in toRemove) {
                node!!.removeComponent(component)
            }

            val components = Component.asArray()
            val list: MutableList<String> = mutableListOf()
            for (component in components) {
                val c = Component.fromStringEnum(component)
                if (c == null) continue
                if (node!!.hasComponent(c)) continue
                if (c == Component.SCRIPTS) continue
                list.add(c.toString())
            }

            combo(" ", selectedAdditionComponent, list.toTypedArray()) { selected ->
                selectedAdditionComponent = selected
            }
            sameLine()
            button("Add") {
                val comp = Component.fromString(list[selectedAdditionComponent])
                if (comp != null) {
                    node!!.addComponent(comp)
                    selectedAdditionComponent = 0
                }
            }
        }

        treeNodeEx("Scripts", 32) {
            val scriptsComponent = node!!.getComponent(Component.SCRIPTS) as ScriptsComponent
            for (script in scriptsComponent.scripts) {
                val name = script::class.simpleName ?: "Unknown Script"
                val properties = script.getExportProperties()
                for (property in properties) {
                    text("${property.name}: ${property.value}")
                    val type = property.property.returnType
                    text(type.toString())
                    if (property.mutable) {
                        sameLine()
                        button("Edit")
                    }
                }
                text(name)
            }
        }
    }
}