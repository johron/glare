package me.johanrong.glare.type

import org.joml.Vector3d
import org.joml.Vector3f

class Transform (
    var position: Vector3d,
    var rotation: Euler,
    var scale: Vector3f
) {
    constructor (): this(
        Vector3d(0.0),
        Euler(0f),
        Vector3f(1f)
    )

    constructor (position: Vector3d): this(
        position,
        Euler(0f),
        Vector3f(1f)
    )

    fun translate(x: Double, y: Double, z: Double): Transform {
        position.add(x, y, z)
        return this
    }
}