package me.johanrong.glare.editor.ui

import me.johanrong.glare.engine.event.EventBus
import me.johanrong.glare.engine.event.NodeSelectedEvent
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.node.component.Component
import me.johanrong.glare.engine.node.component.core.ScriptsComponent
import me.johanrong.glare.engine.ui.IPanel

class PropertiesPanel : IPanel {
    override var name: String = "Properties"
    override var engine: me.johanrong.glare.engine.core.Engine? = null

    private var node: Node? = null

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

        treeNode("Components") {
            val comps = node!!.getComponents()
            for (component in comps) {
                if (component is ScriptsComponent) continue

                text(component.getComponentName())
                sameLine()
                button("X") {
                    println("Removing component: ${component.getComponentName()} from node: ${node!!.name}")
                    node!!.removeComponent(component)
                }
                separator()
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

            combo("+", 0, list.toTypedArray()) { selected ->
                val comp = Component.fromString(list[selected])
                if (comp != null) {
                    println("Adding component: ${comp.getComponentName()} to node: ${node!!.name}")
                    node!!.addComponent(comp)
                }
            }
        }
    }
}