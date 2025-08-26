package me.johanrong.glare.engine.component.physics

import me.johanrong.glare.engine.component.Component

class StaticbodyComponent : RigidbodyComponent() {
    override val type = Component.STATICBODY
    override var freeze: Boolean = true
}