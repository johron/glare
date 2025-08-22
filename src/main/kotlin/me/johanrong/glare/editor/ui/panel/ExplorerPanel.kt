package me.johanrong.glare.editor.ui.panel

import imgui.ImGui
import imgui.flag.ImGuiTreeNodeFlags
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.event.EventBus
import me.johanrong.glare.engine.event.NodeSelectedEvent
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.ui.IPanel

class ExplorerPanel : IPanel {
    override var name: String = "Explorer"
    override var engine: Engine? = null
    private var selectedNode: Node? = null

    override fun render() {
        separator()
        inputText("Search", "") { searchText ->
            TODO("Implement search functionality with: $searchText")
        }

        separator()
        button("Add") {
            engine!!.addNode(Node.builder {
                name = "Node"
                parent = selectedNode ?: engine?.root
            })
        }
        val isSelected = selectedNode == engine?.root
        val flag = if (isSelected) ImGuiTreeNodeFlags.Selected or ImGuiTreeNodeFlags.OpenOnDoubleClick else ImGuiTreeNodeFlags.OpenOnDoubleClick
        treeNodeEx2("Root", flag) {
            if (ImGui.isItemClicked()) {
                selectedNode = engine!!.root
                EventBus.publish(NodeSelectedEvent(engine!!.root))
            }
            makeTree(engine!!.root)
        }
        separator()
    }

    fun makeTree(node: Node) {
        val nodes = node.getChildren()
        for (childNode in nodes) {
            if (childNode.getChildren().isEmpty()) {
                val isSelected = selectedNode == childNode

                selectable(childNode.name, isSelected) {
                    selectedNode = childNode
                    EventBus.publish(NodeSelectedEvent(childNode))
                }
            } else {
                val isSelected = selectedNode == childNode
                val flag = if (isSelected) ImGuiTreeNodeFlags.Selected or ImGuiTreeNodeFlags.OpenOnDoubleClick else ImGuiTreeNodeFlags.OpenOnDoubleClick

                treeNodeEx2(childNode.name, flag) {
                    if (ImGui.isItemClicked()) {
                        selectedNode = childNode
                        EventBus.publish(NodeSelectedEvent(childNode))
                    }
                    makeTree(childNode)
                }
            }
        }
    }
}