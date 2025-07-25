package me.johanrong.glare.math

import org.joml.Math
import org.joml.Matrix4f
import org.joml.Vector3d
import org.joml.Vector3f

data class Transform (
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

    constructor (rotation: Euler): this(
        Vector3d(),
        rotation,
        Vector3f(1f)
    )

    constructor (position: Vector3d, rotation: Euler): this(
        position,
        rotation,
        Vector3f(1f)
    )

    constructor (position: Vector3d, scale: Vector3f): this(
        position,
        Euler(),
        scale,
    )

    constructor (x: Double, y: Double, z: Double): this(Vector3d(x, y, z))

    fun translate(x: Double, y: Double, z: Double) {
        position.add(x, y, z)
    }

    fun getViewMatrix(): Matrix4f {
        val direction = Vector3f()
        direction.x = Math.cos(rotation.toRadians().y * Math.cos(rotation.toRadians().x))
        direction.y = Math.sin(rotation.toRadians().x)
        direction.z =
            Math.sin(rotation.toRadians().y) * Math.cos(rotation.toRadians().x)
        direction.normalize()

        return Matrix4f().lookAt(
            Vector3f(position),
            Vector3f(position).add(direction),
            Vector3f(0f, 1f, 0f)
        )
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

    fun clone(): Transform {
        return Transform(
            Vector3d(position),
            Euler(rotation),
            Vector3f(scale.x, scale.y, scale.z)
        )
    }

    private fun Euler(euler: Euler): Euler {
        return Euler(
            euler.getPitch(),
            euler.getYaw(),
            euler.getRoll()
        )
    }
}