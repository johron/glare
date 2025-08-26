package me.johanrong.glare.engine.type

import org.joml.Vector3f

data class Color(val red: Float, val green: Float, val blue: Float) {
    constructor(color: Float) : this(color, color, color)

    fun toVector3f(): Vector3f {
        return Vector3f(red, green, blue)
    }

    companion object {
        fun fromRGB(red: Int, green: Int, blue: Int): Color {
            return Color(red / 255f, green / 255f, blue / 255f)
        }

        fun fromHex(hex: String): Color {
            require(hex.matches(Regex("^#([A-Fa-f0-9]{6})$"))) { "Hex must be in the format #RRGGBB" }
            val r = hex.substring(1, 3).toInt(16) / 255f
            val g = hex.substring(3, 5).toInt(16) / 255f
            val b = hex.substring(5, 7).toInt(16) / 255f
            return Color(r, g, b)
        }
    }
}