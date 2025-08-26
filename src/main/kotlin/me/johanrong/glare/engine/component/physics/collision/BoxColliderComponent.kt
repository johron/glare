package me.johanrong.glare.engine.component.physics.collision

import me.johanrong.glare.engine.component.Component
import me.johanrong.glare.engine.type.Transform
import org.joml.Vector3f
import org.joml.Vector4f

class BoxColliderComponent(transform: Transform = Transform()) : ColliderComponent(transform) {
    override val type = Component.BOX_COLLIDER

    fun getCorners(): Array<Vector3f> {
        val halfExtents = Vector3f(transform.scale).mul(0.5f)

        val localCorners = arrayOf(
            Vector3f(-halfExtents.x, -halfExtents.y, -halfExtents.z),
            Vector3f( halfExtents.x, -halfExtents.y, -halfExtents.z),
            Vector3f(-halfExtents.x,  halfExtents.y, -halfExtents.z),
            Vector3f( halfExtents.x,  halfExtents.y, -halfExtents.z),
            Vector3f(-halfExtents.x, -halfExtents.y,  halfExtents.z),
            Vector3f( halfExtents.x, -halfExtents.y,  halfExtents.z),
            Vector3f(-halfExtents.x,  halfExtents.y,  halfExtents.z),
            Vector3f( halfExtents.x,  halfExtents.y,  halfExtents.z),
        )

        val matrix = transform.getTransformMatrix()
        return Array(8) { i ->
            val v4 = Vector4f(localCorners[i], 1f).mul(matrix)
            Vector3f(v4.x, v4.y, v4.z)
        }
    }

    // Get the 3 local axes of this OBB in world space
    fun getAxes(): Array<Vector3f> {
        val rot = transform.getTransformMatrix()
        return arrayOf(
            Vector3f(rot.m00(), rot.m01(), rot.m02()).normalize(), // X axis
            Vector3f(rot.m10(), rot.m11(), rot.m12()).normalize(), // Y axis
            Vector3f(rot.m20(), rot.m21(), rot.m22()).normalize()  // Z axis
        )
    }

    fun intersects(b: BoxColliderComponent): Boolean {
        val axes = mutableListOf<Vector3f>()

        // 3 axes from A, 3 from B
        axes.addAll(getAxes())
        axes.addAll(b.getAxes())

        // 9 cross products between edges
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                val axis = Vector3f(getAxes()[i]).cross(b.getAxes()[j])
                if (axis.lengthSquared() > 1e-6f) {
                    axes.add(axis.normalize())
                }
            }
        }

        val aCorners = getCorners()
        val bCorners = b.getCorners()

        // Project both boxes onto each axis
        for (axis in axes) {
            val (minA, maxA) = projectOntoAxis(aCorners, axis)
            val (minB, maxB) = projectOntoAxis(bCorners, axis)

            if (maxA < minB || maxB < minA) {
                return false // gap found
            }
        }
        return true // no gap = intersection
    }

    companion object {

        private fun projectOntoAxis(points: Array<Vector3f>, axis: Vector3f): Pair<Float, Float> {
            var min = axis.dot(points[0])
            var max = min
            for (i in 1 until points.size) {
                val p = axis.dot(points[i])
                if (p < min) min = p
                if (p > max) max = p
            }
            return min to max
        }
    }
}