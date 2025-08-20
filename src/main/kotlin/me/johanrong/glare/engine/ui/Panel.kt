package me.johanrong.glare.engine.ui

import imgui.ImGui
import imgui.type.ImString

open class Panel(val name: String) {
    fun text(text: String) = apply {
        ImGui.text(text)
    }
    fun labelText(label: String, text: String) = apply {
        ImGui.labelText(label, text)
    }
    fun bulletText(text: String) = apply {
        ImGui.bulletText(text)
    }
    fun separator() = apply {
        ImGui.separator()
    }
    fun sameLine() = apply {
        ImGui.sameLine()
    }
    fun spacing() = apply {
        ImGui.spacing()
    }

    fun button(label: String, callback: () -> Unit = {}) = apply {
        if (ImGui.button(label)) {
            callback()
        }
    }
    fun smallButton(label: String, callback: () -> Unit = {}) = apply {
        if (ImGui.smallButton(label)) {
            callback()
        }
    }
    fun arrowButton(id: String, dir: Int, callback: () -> Unit = {}) = apply {
        if (ImGui.arrowButton(id, dir)) {
            callback()
        }
    }
    fun invisibleButton(id: String, width: Float, height: Float, flags: Int = 0, callback: () -> Unit = {}) = apply {
        if (ImGui.invisibleButton(id, width, height, flags)) {
            callback()
        }
    }
    fun checkbox(label: String, active: Boolean) = apply {
        ImGui.checkbox(label, active)
    }
    fun radioButton(label: String, active: Boolean) = apply {
        ImGui.radioButton(label, active)
    }
    fun progressBar(fraction: Float, sizeArgX: Float, sizeArgY: Float, overlay: String? = null) = apply {
        ImGui.progressBar(fraction, sizeArgX, sizeArgY, overlay)
    }

    fun inputText(label: String, text: String, callback: (String) -> Unit) = apply {
        val imString = ImString(text)
        val changed = ImGui.inputText(label, imString)
        if (changed) {
            callback(imString.get())
        }
    }

    init {
        ImGui.begin(name)
    }

    fun build() {
        ImGui.end()
    }
}