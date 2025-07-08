package me.johanrong.glare.node.builtin

import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.Node
import me.johanrong.glare.type.Transform

abstract class Camera(name: String, script: IScript, parent: Node, transform: Transform) : Node(name, parent, script, transform)