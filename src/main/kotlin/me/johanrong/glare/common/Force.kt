package me.johanrong.glare.common

import org.joml.Vector3f

data class Force(val x: Float, val y: Float, val z: Float) : Vector3f(x, y, z) {
    constructor(force: Vector3f) : this(force.x, force.y, force.z)
    constructor(value: Float) : this(value, value, value)

    fun scale(scalar: Float): Force {
        return Force(this.x * scalar, this.y * scalar, this.z * scalar)
    }
}