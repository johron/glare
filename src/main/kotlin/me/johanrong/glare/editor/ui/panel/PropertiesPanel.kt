package me.johanrong.glare.editor.ui.panel

import me.johanrong.glare.editor.ui.field.Field
import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.component.core.ScriptsComponent
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.core.Node
import me.johanrong.glare.engine.event.EventBus
import me.johanrong.glare.engine.event.NodeSelectedEvent
import me.johanrong.glare.engine.ui.IPanel
import me.johanrong.glare.engine.util.getExportedProperties
import me.johanrong.glare.engine.util.getPrettyName

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

            inputVector3d("Position", transform.position) { newPos ->
                transform.position.set(newPos)
            }

            inputEuler("Rotation", transform.rotation) { newRot ->
                transform.rotation.set(newRot)
            }

            inputVector3f("Scale", transform.scale) { newScale ->
                transform.scale.set(newScale)
            }
        }

        val toRemove = mutableListOf<IComponent>()
        for (component in node!!.getComponents()) {
            if (component is ScriptsComponent) continue

            treeNode(getPrettyName(component)) {
                val properties = getExportedProperties(component)
                for (property in properties) {
                    Field(property)
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

        val scriptsComponent = node!!.getComponent(Component.SCRIPTS) as ScriptsComponent
        for (script in scriptsComponent.scripts) {
            val name = getPrettyName(script)

            treeNode(name) {
                val properties = getExportedProperties(script)
                for (property in properties) {
                    Field(property)
                }
                button("Remove") {
                    println("TODO: Remove script: $name from node: ${node!!.name}")
                }
            }
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
}