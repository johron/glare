package me.johanrong.glare.editor.ui

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.event.EventBus
import me.johanrong.glare.engine.event.NodeSelectedEvent
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.ui.IPanel

class ExplorerPanel : IPanel {
    override var name: String = "Explorer"
    override var engine: Engine? = null

    override fun render() {
        separator()
        inputText("Search", "") { searchText ->
            TODO("Implement search functionality with: $searchText")
        }

        separator()
        button("Root")
        sameLine()
        treeNodeEx(" ", 32) {
            makeTree(engine?.root!!)
        }
        separator()
    }

    fun makeTree(node: Node) {
        val nodes = node.getChildren()
        for (node in nodes) {
            button(node.name) {
                EventBus.publish(NodeSelectedEvent(node))
            }
            if (node.getChildren().isNotEmpty()) {
                sameLine()
                treeNode {
                    makeTree(node)
                }
            }
        }
    }
}