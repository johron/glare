package me.johanrong.glare.common

data class Text(var content: String, var size: Float, var color: Color) {
    constructor(content: String) : this(content, 1.0f, Color(1.0f))
}