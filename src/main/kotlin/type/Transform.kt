package me.johanrong.glare.type

import org.joml.Vector3f

class Transform (
    var position: Vector3f,
    var rotation: Euler,
    var scale: Vector3f
) {
    constructor (): this(
        Vector3f(0f),
        Euler(0f),
        Vector3f(1f)
    )

    constructor (position: Vector3f): this(
        position,
        Euler(0f),
        Vector3f(1f)
    )
}