package me.johanrong.glare.engine.render

import imgui.ImGui
import imgui.flag.ImGuiConfigFlags
import imgui.flag.ImGuiDockNodeFlags
import imgui.flag.ImGuiStyleVar
import imgui.flag.ImGuiWindowFlags
import imgui.gl3.ImGuiImplGl3
import imgui.glfw.ImGuiImplGlfw
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.Node

class ImGuiRenderer(val engine: Engine) : IRenderer {
    private val imGuiGlfw = ImGuiImplGlfw()
    private val imGuiGl3 = ImGuiImplGl3()

    private var time = System.nanoTime()
    private var delta = 0.0

    override fun init() {
        val io = ImGui.getIO()
        io.addConfigFlags(ImGuiConfigFlags.DockingEnable) // docking enable should be part of engine config
        imGuiGlfw.init(engine.window.getHandle(), false)
        imGuiGl3.init("#version 460 core")
    }

    override fun render(node: Node) {
        // TODO: limit to 60 FPS

        imGuiGlfw.newFrame();
        ImGui.newFrame();


        // this stuff should be part of engine config, if this is wanted
        val windowFlags = ImGuiWindowFlags.NoDocking
        val viewport = ImGui.getMainViewport()
        ImGui.setNextWindowPos(viewport.workPosX, viewport.workPosY)
        ImGui.setNextWindowSize(viewport.workSizeX, viewport.workSizeY)
        ImGui.setNextWindowViewport(viewport.id)
        ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0f)
        ImGui.pushStyleVar(ImGuiStyleVar.WindowBorderSize, 0f)
        ImGui.pushStyleVar(ImGuiStyleVar.WindowPadding, 0f, 0f)

        // Make the parent window invisible
        ImGui.begin("DockSpace", ImGuiWindowFlags.NoTitleBar or ImGuiWindowFlags.NoCollapse or
                ImGuiWindowFlags.NoResize or ImGuiWindowFlags.NoMove or
                ImGuiWindowFlags.NoBringToFrontOnFocus or ImGuiWindowFlags.NoNavFocus or
                ImGuiWindowFlags.NoBackground or windowFlags)

        ImGui.popStyleVar(3)

        val dockspaceId = ImGui.getID("MyDockSpace")
        // PassthruCentralNode makes the central area transparent
        ImGui.dockSpace(dockspaceId, 0f, 0f, ImGuiDockNodeFlags.PassthruCentralNode)
        ImGui.end()

        for (panel in engine.panels) {
            if (panel.engine == null) panel.engine = engine
            panel.begin()
            panel.render()
            panel.end()
        }

        ImGui.render()
        imGuiGl3.renderDrawData(ImGui.getDrawData())
    }

    override fun cleanup() {
        imGuiGl3.dispose()
        imGuiGlfw.dispose()
        ImGui.destroyContext()
    }
}