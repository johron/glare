package me.johanrong.glare.type

import org.joml.Vector3f

data class Euler (
    private var roll: Double,
    private var pitch: Double,
    private var yaw: Double
) {
    init {
        roll = roll % 360
        pitch = pitch % 360
        yaw = yaw % 360
    }

    constructor (d: Double): this(d, d, d)
    constructor (): this(0.0)

    fun getRoll(): Double = roll
    fun getPitch(): Double = pitch
    fun getYaw(): Double = yaw

    fun addRoll(value: Double) {
        roll = (roll + value) % 360
    }
    fun addPitch(value: Double) {
        pitch = (pitch + value) % 360
    }
    fun addYaw(value: Double) {
        yaw = (yaw + value) % 360
    }

    fun setRoll(value: Double) {
        roll = value % 360
    }

    fun setPitch(value: Double) {
        pitch = value % 360
    }

    fun setYaw(value: Double) {
        yaw = value % 360
    }

    fun toRadians(): Vector3f {
        return Vector3f(
            Math.toRadians(pitch).toFloat(),
            Math.toRadians(yaw).toFloat(),
            Math.toRadians(roll).toFloat(),
        )
    }
}