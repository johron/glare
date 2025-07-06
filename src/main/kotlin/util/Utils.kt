package me.johanrong.glare.util

import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun log(message: String) {
    println("[${Constants.GLARE_ENGINE}] $message")
}

fun storeDataArrayInBuffer(data: FloatArray): FloatBuffer {
    val buffer = MemoryUtil.memAllocFloat(data.size)
    buffer.put(data).flip()
    return buffer
}

fun storeDataArrayInBuffer(data: IntArray): IntBuffer {
    val buffer = MemoryUtil.memAllocInt(data.size)
    buffer.put(data).flip()
    return buffer
}
