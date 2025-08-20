package me.johanrong.glare.editor.ui

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.ui.IPanel

class ExplorerPanel : IPanel {
    override var name: String = "Explorer"
    override var engine: Engine? = null

    override fun render() {
        inputText("Search", "") { searchText ->
            TODO("Implement search functionality with: $searchText")
        }

        button("Root")
        sameLine()
        treeNodeEx(" ", 32) {
            makeTree(engine?.root!!)
        }
    }

    fun makeTree(node: Node) {
        val nodes = node.getChildren()
        for (node in nodes) {
            button(node.name) {
                println("Implement ui explorer events")
            }
            sameLine()
            if (node.getChildren().isNotEmpty()) {
                treeNode {
                    makeTree(node)
                }
            }
        }
    }
}