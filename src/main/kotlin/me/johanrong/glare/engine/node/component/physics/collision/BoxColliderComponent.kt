package me.johanrong.glare.engine.node.component.physics.collision

import me.johanrong.glare.engine.common.Transform

class BoxColliderComponent(var transform: Transform = Transform()) : ColliderComponent() {
    override val colliderType = Collider.BOX
    override var contacts: MutableList<ColliderComponent> = mutableListOf()

    /**
     * Muligens bruke noen innebygde .obj modeller for å brukes i collisjon
     * Kanskje ferdigimportert med bare rå data.
     * BoxCollider må kunne skaleres i alle dimensjoner til å bli rektangulær og hele pakken
     * Muligens rotasjon også
     */

    override fun colliding(other: ColliderComponent): Boolean {
        if (other is BoxColliderComponent) {
            // Get positions from both colliders
            val myPos = node?.transform?.position ?: return false
            val otherPos = other.node?.transform?.position ?: return false

            // Assuming we have size properties in the BoxColliderComponent
            // Add these properties if not already present: var width = 1.0f, var height = 1.0f, var depth = 1.0f

            // Calculate boundaries for each box
            val myMinX = myPos.x - transform.scale.x/2
            val myMaxX = myPos.x + transform.scale.x/2
            val myMinY = myPos.y - transform.scale.y/2
            val myMaxY = myPos.y + transform.scale.y/2
            val myMinZ = myPos.z - transform.scale.z/2
            val myMaxZ = myPos.z + transform.scale.z/2

            val otherMinX = otherPos.x - other.transform.scale.x/2
            val otherMaxX = otherPos.x + other.transform.scale.x/2
            val otherMinY = otherPos.y - other.transform.scale.y/2
            val otherMaxY = otherPos.y + other.transform.scale.y/2
            val otherMinZ = otherPos.z - other.transform.scale.z/2
            val otherMaxZ = otherPos.z + other.transform.scale.z/2

            // AABB collision detection - check overlap on all axes
            return (myMinX <= otherMaxX && myMaxX >= otherMinX &&
                    myMinY <= otherMaxY && myMaxY >= otherMinY &&
                    myMinZ <= otherMaxZ && myMaxZ >= otherMinZ)
        }

        return false // No collision or different collider type
    }
}