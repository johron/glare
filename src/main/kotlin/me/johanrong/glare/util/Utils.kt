package me.johanrong.glare.util

import me.johanrong.glare.core.Engine
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import java.nio.ByteBuffer
import kotlin.jvm.javaClass

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