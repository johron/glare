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
            for (component in node!!.getComponents()) {
                if (component is ScriptsComponent) continue

                text(component.getComponentName())
                sameLine()
                button("X") {
                    node!!.removeComponent(component)
                }
                separator()
            }
            combo("+", 0, Component.asArray()) { selected ->
                val comp = Component.fromString(Component.asArray()[selected])
                if (comp != null) {
                    node!!.addComponent(comp)
                }
            }
        }
    }
}