package me.johanrong.glare.node.base

import me.johanrong.glare.core.IScript
import me.johanrong.glare.node.Node
import me.johanrong.glare.type.Transform

open class Camera (name: String, script: IScript, parent: Node, transform: Transform) : Node(name, parent, script, transform)