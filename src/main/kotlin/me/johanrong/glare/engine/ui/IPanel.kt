package me.johanrong.glare.engine.ui

import imgui.ImGui
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.ui.IImGuiWrapper.Companion.counter

interface IPanel : IImGuiWrapper {
    var name: String
    var engine: Engine?

    fun render()

    fun begin() {
        counter = 0
        ImGui.begin(name)
    }

    fun end() {
        ImGui.end()
    }
}