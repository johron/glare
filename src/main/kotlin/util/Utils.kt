package me.johanrong.glare.util

import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer


fun log(message: String) {
    println("[${GLARE_ENGINE}] $message")
}

fun storeDataInFloatBuffer(data: FloatArray): FloatBuffer {
    val buffer = MemoryUtil.memAllocFloat(data.size)
    buffer.put(data).flip()
    return buffer
}

fun storeDataInIntBuffer(data: IntArray): IntBuffer {
    val buffer = MemoryUtil.memAllocInt(data.size)
    buffer.put(data).flip()
    return buffer
}