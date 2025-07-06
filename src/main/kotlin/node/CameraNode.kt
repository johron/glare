package me.johanrong.glare.node

import me.johanrong.glare.core.IScript
import me.johanrong.glare.type.Transform

abstract class Camera(name: String, script: IScript, parent: Node, transform: Transform) : Node(name, parent, script, transform)