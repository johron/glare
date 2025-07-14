package me.johanrong.glare.util

fun log(message: String) {
    println("[${Constants.GLARE_ENGINE}] $message")
}

fun loadPlain(path: String): String {
    val list = object {}.javaClass.getResourceAsStream(path)?.bufferedReader()?.readLines()
    return list?.joinToString("\n") ?: throw Exception("Resource not found: $path")
}