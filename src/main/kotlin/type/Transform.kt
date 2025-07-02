package me.johanrong.glare.type

import org.joml.Matrix4f
import org.joml.Vector3d
import org.joml.Vector3f

class Transform (
    var position: Vector3d,
    var rotation: Euler,
    var scale: Vector3f,
) {
    constructor (): this(
        Vector3d(0.0),
        Euler(0.0),
        Vector3f(1f)
    )

    constructor (position: Vector3d): this(
        position,
        Euler(0.0),
        Vector3f(1f)
    )

    constructor (x: Double, y: Double, z: Double): this(Vector3d(x, y, z))

    fun translate(x: Double, y: Double, z: Double): Transform {
        position.add(x, y, z)
        return this
    }

    fun getViewMatrix(): Matrix4f {
        return Matrix4f().identity()
            .rotate(rotation.toRadians().x, Vector3f(1f, 0f, 0f))
            .rotate(rotation.toRadians().y, Vector3f(0f, 1f, 0f))
            .rotate(rotation.toRadians().z, Vector3f(0f, 0f, 1f))
            .translate(position.x.toFloat(), -position.y.toFloat(), position.z.toFloat())
    }

    fun getPosition(): Vector3f {
        return Vector3f(position.x.toFloat(), position.y.toFloat(), position.z.toFloat())
    }

    fun getTransformMatrix(): Matrix4f {
        val matrix = Matrix4f()
        matrix.identity()
            .translate(getPosition())
            .rotateX(rotation.toRadians().x)
            .rotateY(rotation.toRadians().y)
            .rotateZ(rotation.toRadians().z)
            .scale(scale)

        return matrix
    }
}