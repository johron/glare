package me.johanrong.glare.type

import org.joml.Vector3f

class Euler (
    private var x: Double,
    private var y: Double,
    private var z: Double
) {
    init {
        x = x % 360
        y = y % 360
        z = z % 360
    }

    constructor (d: Double): this(d, d, d)

    fun getX(): Double = x
    fun getY(): Double = y
    fun getZ(): Double = z

    fun addX(value: Double) {
        x = (x + value) % 360
    }
    fun addY(value: Double) {
        y = (y + value) % 360
    }
    fun addZ(value: Double) {
        z = (z + value) % 360
    }

    fun setX(value: Double) {
        x = value % 360
    }

    fun setY(value: Double) {
        y = value % 360
    }

    fun setZ(value: Double) {
        z = value % 360
    }

    fun toRadians(): Vector3f {
        return Vector3f(
            Math.toRadians(x).toFloat(),
            Math.toRadians(y).toFloat(),
            Math.toRadians(z).toFloat()
        )
    }
}