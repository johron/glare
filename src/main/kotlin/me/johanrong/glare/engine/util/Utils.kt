package me.johanrong.glare.engine.util

import me.johanrong.glare.engine.scripting.Exported
import me.johanrong.glare.engine.scripting.ExportedProperty
import me.johanrong.glare.engine.component.IComponent
import me.johanrong.glare.engine.core.Engine
import me.johanrong.glare.engine.scripting.IScript
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.withNullability
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

fun getExportedProperties(clazz: Any): List<ExportedProperty> {
    val kClass = clazz::class
    return kClass.memberProperties
        .filter { it.findAnnotation<Exported>() != null }
        .map { prop ->
            prop.isAccessible = true
            val ann = prop.findAnnotation<Exported>()!!
            val displayName = getPrettyName(prop.name)
            val type = if (prop.returnType.isMarkedNullable) prop.returnType.withNullability(false) else prop.returnType

            ExportedProperty(
                name = displayName,
                type = type,
                mutable = ann.mutable,
                get = { prop.getter.call(clazz) },
                set = { value ->
                    (prop as? KMutableProperty1<*, *>)
                        ?.setter?.call(clazz, value)
                }
            )
        }
}

fun getPrettyName(script: IScript): String {
    var name = script::class.simpleName ?: "Unknown"
    name += ".kt"
    return name
}

fun getPrettyName(component: IComponent): String {
    var name = component::class.simpleName ?: "Unknown"
    name = name.removeSuffix("Component").replace(Regex("(?<!^)([A-Z])"), " $1")
    return name
}

fun getPrettyName(name: String): String {
    return name.split('_').joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }
}