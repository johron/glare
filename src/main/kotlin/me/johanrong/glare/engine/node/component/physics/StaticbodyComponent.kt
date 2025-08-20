package me.johanrong.glare.engine.node.component.physics

import me.johanrong.glare.engine.node.component.Component

class StaticbodyComponent : RigidbodyComponent() {
    override val type = Component.STATICBODY
    override var freeze: Boolean = true
}