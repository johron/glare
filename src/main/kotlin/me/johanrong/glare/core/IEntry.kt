package me.johanrong.glare.core

interface IEntry {
    fun init(engine: Engine)
    fun setScene(scene: Scene)
    fun update(delta: Double)
    fun cleanup()
}