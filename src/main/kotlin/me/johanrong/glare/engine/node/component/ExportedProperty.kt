package me.johanrong.glare.engine.node.component

import kotlin.reflect.KType

data class ExportedProperty(
    val name: String,
    val type: KType,
    val get: () -> Any?,
    val set: (Any?) -> Unit
)