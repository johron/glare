package me.johanrong.glare.engine.component

import kotlin.reflect.KType

data class ExportedProperty(
    val name: String,
    val type: KType,
    val get: () -> Any?,
    val set: (Any?) -> Unit
)