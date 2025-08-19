package me.johanrong.glare.engine.node.component.physics

class StaticbodyComponent : RigidbodyComponent() {
    override var freeze: Boolean = true
}