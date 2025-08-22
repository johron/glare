package me.johanrong.glare.engine.util

import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.node.component.ExportProperty
import me.johanrong.glare.engine.node.component.ExportPropertyInfo
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

fun log(message: String) {
    println("[${Engine.NAME}] $message")
}

fun loadPlain(path: String): String {
    val list = object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()
    return list?.joinToString("\n") ?: throw Exception("Resource not found: $path")
}

fun loadImage(path: String, stack: MemoryStack): ByteBuffer? {
    object {}.javaClass.classLoader.getResourceAsStream(path).use { res ->
        if (res == null) {
            System.err.println("Resource not found: $path")
            return null
        }

        val bytes = res.readAllBytes()
        val imageBuffer = MemoryUtil.memAlloc(bytes.size)
        imageBuffer.put(bytes)
        imageBuffer.flip()

        return imageBuffer
    }
}

fun Any.getExportProperties(): List<ExportPropertyInfo> {
    val result = mutableListOf<ExportPropertyInfo>()

    this::class.memberProperties.forEach { property ->
        if (property.findAnnotation<ExportProperty>() != null) {
            property.isAccessible = true
            val value = property.getter.call(this)

            result.add(ExportPropertyInfo(
                name = property.name,
                value = value,
                property = property,
                mutable = property.findAnnotation<ExportProperty>()?.mutable ?: true
            ))
        }
    }

    return result
}