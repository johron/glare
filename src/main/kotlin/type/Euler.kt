package me.johanrong.glare.type

import org.joml.Vector3f

class Euler (
    private var x: Float,
    private var y: Float,
    private var z: Float
) {
    init {
        x = x % 360
        y = y % 360
        z = z % 360
    }

    constructor (d: Float): this(d, d, d)

    fun getX(): Float = x
    fun getY(): Float = y
    fun getZ(): Float = z

    fun setX(value: Float) {
        x = value % 360
    }

    fun setY(value: Float) {
        y = value % 360
    }

    fun setZ(value: Float) {
        z = value % 360
    }

    fun toRadians(): Vector3f {
        return Vector3f(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat(),
            Math.toRadians(z.toDouble()).toFloat()
        )
    }
}