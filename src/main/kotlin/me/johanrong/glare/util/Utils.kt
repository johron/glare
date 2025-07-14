package me.johanrong.glare.util

import me.johanrong.glare.core.Engine

fun log(message: String) {
    println("[${Engine.NAME}] $message")
}

fun loadPlain(path: String): String {
    val list = object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()
    return list?.joinToString("\n") ?: throw Exception("Resource not found: $path")
}