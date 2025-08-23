package me.johanrong.glare.engine.ui

import imgui.ImGui
import imgui.type.ImBoolean
import imgui.type.ImFloat
import imgui.type.ImInt
import imgui.type.ImString
import me.johanrong.glare.engine.common.Euler
import org.joml.Vector3d
import org.joml.Vector3f

interface IImGuiWrapper {
    companion object {
        var counter = 0
        fun label(label: String): String {
            return "$label##${counter++}"
        }
    }

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
    fun selectable(label: String, selected: Boolean, callback: (selected: Boolean) -> Unit) = apply {
        val imBool = ImBoolean(selected)
        if (ImGui.selectable(label(label), imBool)) {
            callback(imBool.get())
        }
    }

    fun button(label: String, callback: () -> Unit = {}) = apply {
        if (ImGui.button(label(label))) {
            callback()
        }
    }
    fun smallButton(label: String, callback: () -> Unit = {}) = apply {
        if (ImGui.smallButton(label(label))) {
            callback()
        }
    }
    fun arrowButton(label: String, dir: Int, callback: () -> Unit = {}) = apply {
        if (ImGui.arrowButton(label(label), dir)) {
            callback()
        }
    }
    fun invisibleButton(label: String, width: Float, height: Float, flags: Int = 0, callback: () -> Unit = {}) = apply {
        if (ImGui.invisibleButton(label(label), width, height, flags)) {
            callback()
        }
    }
    fun checkbox(label: String, active: Boolean, callback: (Boolean) -> Unit) = apply {
        val imBool = ImBoolean(active)
        if (ImGui.checkbox(label(label), imBool)) {
            callback(imBool.get())
        }
    }
    fun radioButton(label: String, active: Boolean) = apply {
        ImGui.radioButton(label(label), active)
        println("Need callback maybe? radioButton")
    }
    fun progressBar(fraction: Float, sizeArgX: Float, sizeArgY: Float, overlay: String? = null) = apply {
        ImGui.progressBar(fraction, sizeArgX, sizeArgY, overlay)
    }

    fun inputText(label: String, text: String, callback: (String) -> Unit) = apply {
        val imString = ImString(text)
        val changed = ImGui.inputText(label(label), imString)
        if (changed) {
            callback(imString.get())
        }
    }
    fun inputInt(label: String, value: Int, callback: (Int) -> Unit) = apply {
        val imInt = ImInt(value)
        if (ImGui.inputInt(label(label), imInt)) {
            callback(imInt.get())
        }
    }
    fun inputFloat(label: String, value: Float, callback: (Float) -> Unit) = apply {
        val imFloat = ImFloat(value)
        if (ImGui.inputFloat(label(label), imFloat)) {
            callback(imFloat.get())
        }
    }
    fun inputVector3f(label: String, vector: Vector3f, callback: (Vector3f) -> Unit) = apply {
        if (label.isNotEmpty()) {
            ImGui.text(label)
        }

        val vecX = ImFloat(vector.x)
        val vecY = ImFloat(vector.y)
        val vecZ = ImFloat(vector.z)
        var changed = false

        ImGui.pushItemWidth(60f)
        ImGui.text("x")
        ImGui.sameLine()
        if (ImGui.inputFloat(label("##x"), vecX)) {
            changed = true
        }
        ImGui.popItemWidth()
        ImGui.sameLine()

        ImGui.pushItemWidth(60f)
        ImGui.text("y")
        ImGui.sameLine()
        if (ImGui.inputFloat(label("##y"), vecY)) {
            changed = true
        }
        ImGui.popItemWidth()
        ImGui.sameLine()

        ImGui.pushItemWidth(60f)
        ImGui.text("z")
        ImGui.sameLine()
        if (ImGui.inputFloat(label("##z"), vecZ)) {
            changed = true
        }
        ImGui.popItemWidth()

        if (changed) {
            callback(Vector3f(vecX.get(), vecY.get(), vecZ.get()))
        }
    }
    fun inputVector3d(label: String, vector: Vector3d, callback: (Vector3d) -> Unit) = apply {
        val vec = Vector3f(vector.x.toFloat(), vector.y.toFloat(), vector.z.toFloat())
        inputVector3f(label, vec) {
            callback(Vector3d(vec[0].toDouble(), vec[1].toDouble(), vec[2].toDouble()))
        }
    }
    fun inputEuler(label: String, euler: Euler, callback: (Euler) -> Unit) = apply {
        val vec = Vector3f(euler.getRoll().toFloat(), euler.getPitch().toFloat(), euler.getYaw().toFloat())
        inputVector3f(label, vec) {
            callback(Euler(vec[0].toDouble(), vec[1].toDouble(), vec[2].toDouble()))
        }
    }

    fun combo(label: String, currentItem: Int, items: Array<String>, callback: (Int) -> Unit) = apply {
        val imInt = ImInt(currentItem)
        if (ImGui.combo(label(label), imInt, items)) {
            callback(imInt.get())
        }
    }

    fun treeNode(label: String = " ", callback: () -> Unit): Boolean {
        val opened = ImGui.treeNode(label(label))
        if (opened) {
            callback()
            ImGui.treePop()
        }
        return opened
    }
    fun treeNodeEx(label: String, flags: Int, callback: () -> Unit): Boolean {
        val opened = ImGui.treeNodeEx(label(label), flags)
        if (opened) {
            callback()
            ImGui.treePop()
        }
        return opened
    }
    fun treeNodeEx2(label: String, flags: Int, callback: () -> Unit): Boolean {
        val result = ImGui.treeNodeEx(label(label), flags)
        // Call the callback when clicked (regardless of open state)
        if (ImGui.isItemClicked()) {
            callback()
        }
        if (result) {
            callback()
            ImGui.treePop()
        }
        return result
    }
}