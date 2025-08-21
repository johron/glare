package me.johanrong.glare.engine.node.component

import kotlin.reflect.KProperty

data class ExportPropertyInfo(
    val name: String,
    val value: Any?,
    val mutable: Boolean,
    val property: KProperty<*>
)