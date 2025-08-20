package me.johanrong.glare.editor.ui

import me.johanrong.glare.engine.ui.IPanel
import me.johanrong.glare.engine.ui.Panel

class ExplorerPanel : IPanel {
    override fun build() {
        Panel("Explorer")
            .text("test")
            .build()
    }
}