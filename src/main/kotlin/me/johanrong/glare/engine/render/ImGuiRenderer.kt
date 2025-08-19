package me.johanrong.glare.engine.render

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.Node
import me.johanrong.glare.engine.ui.Panel


class ImGuiRenderer(val engine: Engine) : IRenderer {
    private val imGuiGlfw = ImGuiImplGlfw()
    private val imGuiGl3 = ImGuiImplGl3()

    private var time = System.nanoTime()
    private var delta = 0.0

    override fun init() {
        val io = ImGui.getIO()
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable)
        imGuiGlfw.init(engine.window.getHandle(), false)
        imGuiGl3.init("#version 460 core")
    }

    override fun render(node: Node) {
        if (System.nanoTime() - time > Engine.NANOSECOND / 20) {
            delta = engine.getDelta()
            time = System.nanoTime()
        }

        imGuiGlfw.newFrame();
        ImGui.newFrame();

        Panel("Glare Engine")
            .text("Version - ${Engine.VERSION}")
            .text("FPS - ${String.format("%.2f", 1000.0/delta)}")
            .separator()
            .inputText("Search", "", callback = { searchText ->
                // Handle search text input
            })
            .build()


        ImGui.render()
        imGuiGl3.renderDrawData(ImGui.getDrawData())
    }

    override fun cleanup() {
        imGuiGl3.dispose()
        imGuiGlfw.dispose()
        ImGui.destroyContext()
    }
}